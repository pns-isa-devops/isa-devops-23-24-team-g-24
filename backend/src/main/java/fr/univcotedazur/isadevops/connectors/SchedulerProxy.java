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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Component
public class SchedulerProxy implements Scheduler {

        private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerProxy.class);

    //@Value("${scheduler.host.baseurl}")
    private String schedulerHostandPort = "http://localhost:9091";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<String> book(String dateBook, String nameActivity, String namePartner) {
        LOGGER.info("ON ENTRE DANS LE PROXY COTE BACKEND");
        try {
            ResponseEntity<BookReceiptDTO> result = restTemplate.postForEntity(
                    schedulerHostandPort + "/scheduler",
                    new BookRequestDTO(dateBook, nameActivity, namePartner),
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