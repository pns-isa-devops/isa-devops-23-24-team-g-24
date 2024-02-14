package fr.univcotedazur.isadevops.services;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> findAllActivities() {
        return activityRepository.findAll();
    }
}

