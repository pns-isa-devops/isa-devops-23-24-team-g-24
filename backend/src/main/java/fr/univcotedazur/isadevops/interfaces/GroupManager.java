package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.UserGroup;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;

import java.util.List;

public interface GroupManager {
    public UserGroup createGroup(String name);
    public void addMemberToGroup(Long groupId, Long customerId) throws CustomerIdNotFoundException;
    public boolean deleteGroup(Long groupId);
    public List<UserGroup> getAllGroups();
}
