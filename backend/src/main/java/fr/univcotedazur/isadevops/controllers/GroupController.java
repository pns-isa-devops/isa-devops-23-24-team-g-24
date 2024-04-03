package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.components.GroupHandler;
import fr.univcotedazur.isadevops.dto.GroupDTO;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.entities.UserGroup;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.isadevops.interfaces.GroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = GroupController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class GroupController {
    private final GroupManager groupManager;
    public static final String BASE_URI = "/groups";

    @Autowired
    public GroupController(GroupHandler groupService) {
        this.groupManager = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody String name) {
        UserGroup group = groupManager.createGroup(name);
        return new ResponseEntity<>(convertToDTO(group), HttpStatus.CREATED);
    }

    @PostMapping("/{groupId}/members/{customerId}")
    public ResponseEntity<Void> addMemberToGroup(@PathVariable Long groupId, @PathVariable Long customerId) throws CustomerIdNotFoundException {
        groupManager.addMemberToGroup(groupId, customerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        boolean deleted = groupManager.deleteGroup(groupId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<UserGroup> groups = groupManager.getAllGroups();
        List<GroupDTO> groupDTOs = groups.stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok(groupDTOs);
    }

    GroupDTO convertToDTO(UserGroup group) {
        return new GroupDTO(group.getId(), group.getName(), group.getMembers().stream()
                .map(Customer::getId)
                .collect(Collectors.toSet()));
    }
}

