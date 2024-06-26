package fr.univcotedazur.isadevops.connectors;

import fr.univcotedazur.isadevops.connectors.externaldto.BookReceiptDTO;
import fr.univcotedazur.isadevops.connectors.externaldto.BookRequestDTO;
import fr.univcotedazur.isadevops.interfaces.Scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;

@Component
public class SchedulerProxy implements Scheduler {

    @Value("${scheduler.host.baseurl}")
    private String schedulerHostandPort;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<String> book(String nameActivity, String namePartner) {
        System.out.println("ON ENTRE DANS LE SCHEDULER");
        try {
            ResponseEntity<BookReceiptDTO> result = restTemplate.postForEntity(
                    schedulerHostandPort + "/scheduler",
                    new BookRequestDTO(nameActivity, namePartner),
                    BookReceiptDTO.class
            );
            if (result.getStatusCode().equals(HttpStatus.CREATED) && result.hasBody()) {
                return Optional.of(result.getBody().bookReceiptId());
            } else {
                return Optional.empty();
            }
        }
        catch (RestClientResponseException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return Optional.empty();
            }
            throw errorException;
        }
    }

}
