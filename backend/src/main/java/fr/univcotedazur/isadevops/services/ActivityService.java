package fr.univcotedazur.isadevops.services;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.isadevops.interfaces.ActivityCreator;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService implements ActivityCreator {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> findAllActivities() {
        return activityRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Activity> findByName(String name) {
        return activityRepository.findActivityByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Activity> findById(long id) {
        return activityRepository.findById(id);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }
    @Override
    @Transactional
    public Activity create(String name, String localisation, long numberOfPlaces) throws AlreadyExistingActivityException{
        if (findByName(name).isPresent())
            throw new AlreadyExistingActivityException(name);
        Activity newactivity = new Activity(name, localisation, numberOfPlaces,5, 10, 10);
        return activityRepository.save(newactivity);
    }
}

