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
import java.util.stream.Collectors;

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

    @ShellMethod("List all activities")
    public String listActivities() {
        System.out.println("Fetching activities from server...");
        CliActivity[] activities = restTemplate.getForObject(BASE_URI, CliActivity[].class);
        if (activities == null || activities.length == 0) return "No activities found.";
        return Arrays.stream(activities).map(Object::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod("Add activity (add-activity ACTIVITY_NAME LOCATION NUMBER_OF_PLACES POINTS_EARNED PRICE PRICE_POINTS)")
    public String addActivity(String name, String location, Long numberOfPlaces, Long pointsEarned, double price, Long pricePoints) {
        CliActivity createdActivity = restTemplate.postForObject(BASE_URI, new CliActivity(name, location, numberOfPlaces, pointsEarned, price, pricePoints), CliActivity.class);
        return createdActivity != null ? "Activity added with success: " + createdActivity.toString() : "Error while adding activity";
    }

}
