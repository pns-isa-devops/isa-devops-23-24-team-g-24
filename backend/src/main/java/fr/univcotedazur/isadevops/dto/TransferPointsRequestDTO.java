package fr.univcotedazur.isadevops.dto;

public class TransferPointsRequestDTO {
    private final Long fromCustomerId;
    private final Long toCustomerId;
    private final double points;

    public TransferPointsRequestDTO(Long fromCustomerId, Long toCustomerId, double points) {
        this.fromCustomerId = fromCustomerId;
        this.toCustomerId = toCustomerId;
        this.points = points;
    }

    public Long getFromCustomerId() {
        return fromCustomerId;
    }


    public Long getToCustomerId() {
        return toCustomerId;
    }

    public double getPoints() {
        return points;
    }

}

