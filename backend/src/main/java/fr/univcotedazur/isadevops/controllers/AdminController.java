package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.components.*;
import fr.univcotedazur.isadevops.dto.ErrorDTO;
import fr.univcotedazur.isadevops.dto.PartnerDTO;
import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingPartnerException;
import fr.univcotedazur.isadevops.exceptions.PartnerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path=AdminController.BASE_URI,produces=APPLICATION_JSON_VALUE)
public class AdminController {
    private final PartnerRegistry partnerRegistry;
    public static final String BASE_URI = "/admin";

    @Autowired
    public AdminController(PartnerRegistry partnerRegistry){
        this.partnerRegistry=partnerRegistry;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e){
        return new ErrorDTO("Cannot process Partner information", e.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<Partner>> getAllPartners(){
        List<Partner> partners= partnerRegistry.findAllPartners();
        System.out.println("Fetching partners");
        return ResponseEntity.ok(partnerRegistry.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable Long id) {
        Optional<Partner> optionalPartner = partnerRegistry.findById(id);

        return optionalPartner.map(partner -> ResponseEntity.ok(convertPartnerToDTO(partner)))
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePartner(@PathVariable Long id){
        System.out.println("Deleting partner");
        try {
            partnerRegistry.delete(id);
        }catch (PartnerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partner ID not found");
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PartnerDTO> addPartner(@RequestBody @Valid PartnerDTO partner){
        System.out.println("Adding partner");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertPartnerToDTO(partnerRegistry.create(partner.name(), partner.location(), partner.description())));
            }catch (AlreadyExistingPartnerException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    private static PartnerDTO convertPartnerToDTO(Partner partner){
        return new PartnerDTO(partner.getId(),partner.getName(), partner.getLocation(), partner.getDescription());
    }

}
