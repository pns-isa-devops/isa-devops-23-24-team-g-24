package fr.univcotedazur.isadevops.connectors;

import fr.univcotedazur.isadevops.connectors.externaldto.PaymentReceiptDTO;
import fr.univcotedazur.isadevops.connectors.externaldto.PaymentRequestDTO;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.interfaces.Bank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class BankProxy implements Bank {

    @Value("${bank.host.baseurl}")
    private String bankHostandPort;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<String> pay(Customer customer, double value) {
        try {
            ResponseEntity<PaymentReceiptDTO> result = restTemplate.postForEntity(
                    bankHostandPort + "/cctransactions",
                    new PaymentRequestDTO(customer.getCreditCard(), value),
                    PaymentReceiptDTO.class
            );
            if (result.getStatusCode().equals(HttpStatus.CREATED) && result.hasBody()) {
                return Optional.of(result.getBody().payReceiptId());
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
