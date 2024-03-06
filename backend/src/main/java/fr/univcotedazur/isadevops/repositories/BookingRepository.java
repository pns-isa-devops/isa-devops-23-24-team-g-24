package fr.univcotedazur.isadevops.repositories;

import fr.univcotedazur.isadevops.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findByActivityId(Long activityId);
}
