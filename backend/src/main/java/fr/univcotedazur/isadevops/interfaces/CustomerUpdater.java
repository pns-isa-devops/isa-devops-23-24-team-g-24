package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.entities.UserGroup;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.NotEnoughPointsException;

public interface CustomerUpdater {
    public void transferPoints(Long fromCustomerId, Long toCustomerId, double points) throws CustomerIdNotFoundException, NotEnoughPointsException;
    public void updateCustomer(Customer customer);
    public void setGroup(Long customerId, UserGroup group) throws CustomerIdNotFoundException;
}
