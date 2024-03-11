package fr.univcotedazur.isadevops.connectors.externaldto;

// External DTO (Data Transfert Object) to POST payment request to the external Bank system
public record PaymentRequestDTO (String creditCard, double amount)
{
}
