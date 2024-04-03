package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;

import java.util.List;
import java.util.Optional;

public interface ActivityFinder {
    Optional<Activity> findByName(String name);
    Optional<Activity> findById(long id);
    List<Activity> findAllActivities();
}
