package fr.univcotedazur.isadevops.cli.commands;

import fr.univcotedazur.isadevops.cli.CliContext;
import fr.univcotedazur.isadevops.cli.model.CliActivity;
import fr.univcotedazur.isadevops.cli.model.CliCustomer;
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
public class ActivityCommands {

    public static final String BASE_URI = "/activities";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;

    @Autowired
    public ActivityCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }


    @ShellMethod("List all known activities")
    public String activities() {
        return cliContext.getCustomers().toString();
    }

    @ShellMethod("Update all known activities from server")
    public String updateActivities() {
        Map<String, CliActivity> customerMap = cliContext.getActivities();
        return customerMap.toString();
    }


}
