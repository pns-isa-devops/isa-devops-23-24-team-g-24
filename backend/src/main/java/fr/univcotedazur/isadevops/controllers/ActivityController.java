package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.dto.ActivityDTO;
import fr.univcotedazur.isadevops.dto.ErrorDTO;
import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import fr.univcotedazur.isadevops.components.ActivityService;
import fr.univcotedazur.isadevops.interfaces.ActivityCreator;
import fr.univcotedazur.isadevops.interfaces.ActivityFinder;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

@RestController
@RequestMapping(path = ActivityController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class ActivityController {
    private final ActivityCreator activityCreator;
    private final ActivityFinder activityFinder;
    public static final String BASE_URI = "/activities";
    private static final Logger LOG = LoggerFactory.getLogger(ActivityController.class);


    @Autowired
    public ActivityController(ActivityCreator activityCreator, ActivityFinder activityFinder) {
        this.activityCreator = activityCreator;
        this.activityFinder = activityFinder;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        return new ErrorDTO("Cannot process Activity information", e.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityFinder.findAllActivities();
        LOG.info("Fetching activities");
        return ResponseEntity.ok(activities);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityDTO> addActivity(@RequestBody @Valid ActivityDTO activity) {
        LOG.info("Adding activity");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertActivityToDTO(activityCreator.create(activity.name(), activity.location(), activity.numberOfPlaces(),activity.price(), activity.pricePoints(), activity.idPartner())));
        } catch (AlreadyExistingActivityException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    private static ActivityDTO convertActivityToDTO(Activity activity) {
        return new ActivityDTO(activity.getId(), activity.getName(), activity.getLocation(), activity.getNumberOfPlaces(),activity.getPrice(), activity.getPricePoints(), activity.getPartner().getId());
    }
}
