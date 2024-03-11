package fr.univcotedazur.isadevops.connectors.externaldto;

// External DTO to receive a book receipt from a successful POST book request to the external Library system
public record BookRequestDTO (String dateBook, String nameActivity, String namePartner)
{
}
