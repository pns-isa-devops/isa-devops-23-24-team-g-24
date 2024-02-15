package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.dto.ActivityDTO;
import fr.univcotedazur.isadevops.dto.CustomerDTO;
import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.isadevops.services.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {
    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.findAllActivities();
        System.out.println("Fetching activities");
        return ResponseEntity.ok(activities);
    }
    @PostMapping
    public ResponseEntity<ActivityDTO> addActivity(@RequestBody @Valid ActivityDTO activity) {
        System.out.println("Adding activity");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertActivityToDTO(activityService.create(activity.name(), activity.location(), activity.numberOfPlaces())));
        } catch (AlreadyExistingActivityException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    private static ActivityDTO convertActivityToDTO(Activity activity) { // In more complex cases, we could use a ModelMapper such as MapStruct
        return new ActivityDTO(activity.getId(), activity.getName(), activity.getLocation(), activity.getNumberOfPlaces());
    }
}
