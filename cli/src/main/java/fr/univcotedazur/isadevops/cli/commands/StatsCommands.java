package fr.univcotedazur.isadevops.cli.commands;

import fr.univcotedazur.isadevops.cli.CliContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
public class StatsCommands {

    public static final String BASE_URI = "/stats";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;
    @Autowired
    public StatsCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Get stats for an activity")
    public String getStatsActivity(Long activityId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(BASE_URI + "/activity/" + activityId, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return "Failed to get stats for activity " + activityId;
            }
        } catch (HttpClientErrorException e) {
            return "Failed to get stats for activity " + activityId;
        }
    }

    @ShellMethod("Get stats for a customer")
    public String getStatsCustomer(Long customerId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(BASE_URI + "/customer/" + customerId, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return "Failed to get stats for customer " + customerId;
            }
        } catch (HttpClientErrorException e) {
            return "Failed to get stats for customer " + customerId;
        }
    }

    @ShellMethod("Get stats for a partner")
    public String getStatsPartner(Long partnerId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(BASE_URI + "/partner/" + partnerId, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return "Failed to get stats for partner " + partnerId;
            }
        } catch (HttpClientErrorException e) {
            return "Failed to get stats for partner " + partnerId;
        }
    }
}
