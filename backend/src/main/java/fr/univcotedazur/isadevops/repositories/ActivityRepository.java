package fr.univcotedazur.isadevops.repositories;

import fr.univcotedazur.isadevops.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}

