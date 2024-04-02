package fr.univcotedazur.isadevops.cli.commands;

import fr.univcotedazur.isadevops.cli.model.CliGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class GroupCommands {

    private final RestTemplate restTemplate;
    public static final String BASE_URI = "/groups";

    public GroupCommands(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ShellMethod("Crée un nouveau groupe")
    public String createGroup(String name) {
        ResponseEntity<CliGroup> response = restTemplate.postForEntity(BASE_URI, name, CliGroup.class);
        return response.getStatusCode() == HttpStatus.CREATED ? "Groupe créé avec succès" : "Échec de la création du groupe";
    }

    @ShellMethod("Ajoute un membre à un groupe")
    public String addMemberToGroup(Long groupId, Long customerId) {
        restTemplate.postForEntity(BASE_URI + groupId + "/members/" + customerId, null, Void.class);
        return "Membre ajouté au groupe";
    }
    @ShellMethod
    public String deleteGroup(Long groupId) {
        restTemplate.delete(BASE_URI + groupId);
        return "Groupe supprimé";
    }
    @ShellMethod
    public String getAllGroups() {
        return restTemplate.getForObject(BASE_URI, String.class);
    }
}

