package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import fr.univcotedazur.isadevops.interfaces.ActivityCreator;
import fr.univcotedazur.isadevops.interfaces.ActivityFinder;
import fr.univcotedazur.isadevops.interfaces.PartnerFinder;
import fr.univcotedazur.isadevops.interfaces.PartnerManager;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import fr.univcotedazur.isadevops.repositories.PartnerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService implements ActivityCreator, ActivityFinder {
    private final ActivityRepository activityRepository;
    private final PartnerFinder partnerFinder;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, PartnerFinder partnerFinder) {
        this.activityRepository = activityRepository;
        this.partnerFinder = partnerFinder;
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
    public Activity create(String name, String localisation, long numberOfPlaces,double price, long pricePoints, long id_partner) throws AlreadyExistingActivityException{
        if (findByName(name).isPresent())
            throw new AlreadyExistingActivityException(name);
        Activity newactivity = new Activity(name, localisation, numberOfPlaces, price, pricePoints);
        if(id_partner != 0){
            partnerFinder.findById(id_partner).ifPresent(newactivity::setPartner);
            newactivity.setPartner(partnerFinder.findById(id_partner).get());
        }
        return activityRepository.save(newactivity);
    }
}

