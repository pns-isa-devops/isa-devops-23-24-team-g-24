package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.dto.ActivityDTO;
import fr.univcotedazur.isadevops.dto.CustomerDTO;
import fr.univcotedazur.isadevops.dto.ErrorDTO;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

@RestController
@RequestMapping(path = ActivityController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class ActivityController {
    private final ActivityService activityService;

    public static final String BASE_URI = "/activities";


    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        return new ErrorDTO("Cannot process Activity information", e.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.findAllActivities();
        System.out.println("Fetching activities");
        return ResponseEntity.ok(activities);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityDTO> addActivity(@RequestBody @Valid ActivityDTO activity) {
        System.out.println("Adding activity");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertActivityToDTO(activityService.create(activity.name(), activity.location(), activity.numberOfPlaces(), activity.pointsEarned(), activity.price(), activity.pricePoints(), activity.idPartner())));
        } catch (AlreadyExistingActivityException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }



    private static ActivityDTO convertActivityToDTO(Activity activity) { // In more complex cases, we could use a ModelMapper such as MapStruct
        return new ActivityDTO(activity.getId(), activity.getName(), activity.getLocation(), activity.getNumberOfPlaces(), activity.getPointEarned(), activity.getPrice(), activity.getPricePoints(), activity.getPartner().getId());
    }
}
