package fr.univcotedazur.isadevops.interfaces;

import java.util.Optional;
//Scheduler pour notre deuxième service externe
public interface Scheduler {

    Optional<String> book(String nameActivity, String namePartner);
}
