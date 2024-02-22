package fr.univcotedazur.isadevops.repositories;

import fr.univcotedazur.isadevops.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findPartnerByName(String name);
    Optional<Partner> findById(long id);


}
