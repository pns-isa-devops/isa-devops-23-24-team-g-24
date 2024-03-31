package fr.univcotedazur.isadevops.cli.commands;

import fr.univcotedazur.isadevops.cli.CliContext;
import fr.univcotedazur.isadevops.cli.model.CliBooking;
import fr.univcotedazur.isadevops.cli.model.CliCustomer;
import fr.univcotedazur.isadevops.cli.model.CliTransferPointsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@ShellComponent
public class CustomerCommands {

    public static final String BASE_URI = "/customers";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;

    @Autowired
    public CustomerCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Register a customer in the CoD backend (register CUSTOMER_NAME CREDIT_CARD_NUMBER)")
    public CliCustomer register(String name, String creditCard) {
        CliCustomer res = restTemplate.postForObject(BASE_URI, new CliCustomer(name, creditCard), CliCustomer.class);
        cliContext.getCustomers().put(Objects.requireNonNull(res).getName(), res);
        return res;
    }

    @ShellMethod("List all known customers")
    public String customers() {
        return cliContext.getCustomers().toString();
    }

    @ShellMethod("Update all known customers from server")
    public String updateCustomers() {
        Map<String, CliCustomer> customerMap = cliContext.getCustomers();
        customerMap.clear();
        customerMap.putAll(Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(BASE_URI, CliCustomer[].class)
                        .getBody())).collect(toMap(CliCustomer::getName, Function.identity())));
        return customerMap.toString();
    }
    @ShellMethod("Transfer points from a user to another (transfer-points FROM_CUSTOMER_ID TO_CUSTOMER_ID POINTS)")
    public String transferPoints(Long fromCustomerId, Long toCustomerId, int points) {
        restTemplate.postForEntity(BASE_URI + "/transferPoints", new CliTransferPointsRequest(fromCustomerId,toCustomerId,points), CliTransferPointsRequest.class);
        return "Points transferred";
    }


}
