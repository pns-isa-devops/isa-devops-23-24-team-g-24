package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.entities.UserGroup;
import fr.univcotedazur.isadevops.exceptions.NotEnoughPointsException;
import fr.univcotedazur.isadevops.interfaces.CustomerFinder;
import fr.univcotedazur.isadevops.interfaces.CustomerUpdater;
import fr.univcotedazur.isadevops.repositories.CustomerRepository;
import fr.univcotedazur.isadevops.repositories.GroupRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferPointTest {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private GroupRepository groupRepository;
    @Autowired
    private CustomerUpdater customerUpdater;

    private Customer fromCustomer;
    private Customer toCustomerSameGroup;
    private Customer toCustomerDifferentGroup;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fromCustomer = new Customer("John Doe", "1234567890", 100);
        fromCustomer.setId(1L);
        toCustomerSameGroup = new Customer("Jane Doe", "0987654321", 50);
        toCustomerSameGroup.setId(2L);
        toCustomerDifferentGroup = new Customer("Jim Beam", "1122334455", 20);
        toCustomerDifferentGroup.setId(3L);
        UserGroup groupA = new UserGroup("Groupe A", Set.of(fromCustomer, toCustomerSameGroup));
        UserGroup groupB = new UserGroup("Groupe B", Set.of(toCustomerDifferentGroup));
        fromCustomer.setGroup(groupA);
        toCustomerSameGroup.setGroup(groupA);
        toCustomerDifferentGroup.setGroup(groupB);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(groupA));
        when(groupRepository.findById(2L)).thenReturn(Optional.of(groupB));
    }

    @Test
    void transferPointsWithinSameGroup_shouldNotIncurPenalty() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(fromCustomer));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(toCustomerSameGroup));
        when(customerRepository.findById(3L)).thenReturn(Optional.of(toCustomerDifferentGroup));
        fromCustomer.setPointsBalance(100);
        toCustomerSameGroup.setPointsBalance(50);
        customerUpdater.transferPoints(fromCustomer.getId(), toCustomerSameGroup.getId(), 10);
        assertEquals(90, fromCustomer.getPointsBalance());
        assertEquals(60, toCustomerSameGroup.getPointsBalance());
    }

    @Test
    void transferPointsToDifferentGroup_shouldIncurPenalty() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(fromCustomer));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(toCustomerSameGroup));
        when(customerRepository.findById(3L)).thenReturn(Optional.of(toCustomerDifferentGroup));
        fromCustomer.setPointsBalance(100);
        toCustomerDifferentGroup.setPointsBalance(20);
        customerUpdater.transferPoints(fromCustomer.getId(), toCustomerDifferentGroup.getId(), 10);
        assertEquals(90, fromCustomer.getPointsBalance());
        assertEquals(29, toCustomerDifferentGroup.getPointsBalance());
    }

    @Test
    void transferPointsWithInsufficientBalance_shouldThrowException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(fromCustomer));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(toCustomerSameGroup));
        when(customerRepository.findById(3L)).thenReturn(Optional.of(toCustomerDifferentGroup));
        assertThrows(NotEnoughPointsException.class, () -> customerUpdater.transferPoints(fromCustomer.getId(), toCustomerSameGroup.getId(), 200));
    }
}
