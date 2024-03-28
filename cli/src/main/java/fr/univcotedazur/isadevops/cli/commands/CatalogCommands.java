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
public class CatalogCommands {

    public static final String BASE_URI = "/activities";
    private final RestTemplate restTemplate;
    private final CliContext cliContext;

    @Autowired
    public CatalogCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Retrieve extras (activities with only price points)")
    public String retrieveExtras() {
        String ret = "";
        System.out.println("Fetching extras from server...");
        CliActivity[] activities = restTemplate.getForObject(BASE_URI, CliActivity[].class);
        for(CliActivity activity : activities) {
            if(activity.getPrice()==-1 && activity.getPricePoints()>=0) {
                ret += "Extra's name :"+ activity.getName() +" Extra's price : "+activity.getPricePoints()+ "\n";
            }
        }
        return ret;
    }

    @ShellMethod("Retrieve activities (activities at least one price in €)")
    public String retrieveActivities() {
        String ret = "";
        System.out.println("Fetching activities from server...");
        CliActivity[] activities = restTemplate.getForObject(BASE_URI, CliActivity[].class);
        for(CliActivity activity : activities) {
            if(activity.getPrice()>=0) {
                ret += "Activiy's name :"+ activity.getName() +" Activiy's price €: "+activity.getPrice()
                + " Activiy's price points : "+activity.getPricePoints()+ "\n";
            }
        }
        return ret;
    }
}
