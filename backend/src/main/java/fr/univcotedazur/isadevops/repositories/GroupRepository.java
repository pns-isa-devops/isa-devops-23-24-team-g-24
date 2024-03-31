package fr.univcotedazur.isadevops.repositories;

import fr.univcotedazur.isadevops.entities.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<UserGroup, Long> {
}

