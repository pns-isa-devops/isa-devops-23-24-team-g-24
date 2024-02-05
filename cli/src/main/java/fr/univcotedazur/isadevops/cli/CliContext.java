package fr.univcotedazur.isadevops.cli;

import fr.univcotedazur.isadevops.cli.model.CliCustomer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CliContext {

    private Map<String, CliCustomer> customers;

    public Map<String, CliCustomer> getCustomers() {
        return customers;
    }

    public CliContext() {
        customers = new HashMap<>();
    }

    @Override
    public String toString() {
        return customers.keySet().stream()
                .map(key -> key + "=" + customers.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

}
