package fr.univcotedazur.isadevops.interfaces;

import java.util.Optional;

public interface Scheduler {

    Optional<String> book(String dateBook, String nameActivity, String namePartner);
}
