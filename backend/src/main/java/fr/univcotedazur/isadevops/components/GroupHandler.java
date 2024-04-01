package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.UserGroup;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.isadevops.interfaces.CustomerUpdater;
import fr.univcotedazur.isadevops.repositories.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupHandler {

    private final GroupRepository groupRepository;
    private final CustomerUpdater customerUpdater;

    @Autowired
    public GroupHandler(GroupRepository groupRepository, CustomerUpdater customerUpdater) {
        this.groupRepository = groupRepository;
        this.customerUpdater = customerUpdater;
    }

    @Transactional
    public UserGroup createGroup(String name) {
        UserGroup group = new UserGroup();
        group.setName(name);
        return groupRepository.save(group);
    }

    @Transactional
    public void addMemberToGroup(Long groupId, Long customerId) throws CustomerIdNotFoundException {
        UserGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Groupe non trouvé"));
        customerUpdater.setGroup(customerId, group);
    }

    @Transactional
    public boolean deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
        return true;
    }

    @Transactional
    public List<UserGroup> getAllGroups() {
        return groupRepository.findAll();
    }
}
