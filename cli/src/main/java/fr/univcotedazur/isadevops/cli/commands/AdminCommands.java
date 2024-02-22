package fr.univcotedazur.isadevops.cli.commands;

import fr.univcotedazur.isadevops.cli.CliContext;
import fr.univcotedazur.isadevops.cli.model.CliAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class AdminCommands {
    public static final String BASE_URI = "/admin";
    private final RestTemplate restTemplate;
    private final CliContext cliContext;

    @Autowired
    public AdminCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }

    @ShellMethod("Add partner")
    public String addPartner(String name, String location, String description){
        CliAdmin createdPartner= restTemplate.postForObject(BASE_URI, new CliAdmin(name, location, description), CliAdmin.class);
        System.out.println("Adding partner");
        return createdPartner !=null ? "Partner added with success: " + createdPartner.toString() : "Error while adding partner";
    }

}
