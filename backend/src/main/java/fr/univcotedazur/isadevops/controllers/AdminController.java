package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.dto.PartnerDTO;
import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path=AdminController.BASE_URI,produces=APPLICATION_JSON_VALUE)
public class AdminController {
    private final AdminService adminService;
    public static final String BASE_URI = "/admin";

    @Autowired
    public AdminController(AdminService adminService){this.adminService=adminService;}

    @PostMapping
    public ResponseEntity<PartnerDTO> addPartner(@RequestBody @Valid PartnerDTO partner){
        System.out.println("Adding partner");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertPartnerToDTO(adminService.create(partner.name(), partner.location(), partner.description())));
            }catch (AlreadyExistingActivityException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    private static PartnerDTO convertPartnerToDTO(Partner partner){
        return new PartnerDTO(partner.getId(),partner.getName(), partner.getLocation(), partner.getDescription());
    }

}
