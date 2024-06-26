package fr.univcotedazur.isadevops.cli.model;

public class CliTransferPointsRequest {
    private Long fromCustomerId;
    private Long toCustomerId;
    private double points;

    public CliTransferPointsRequest(Long fromCustomerId, Long toCustomerId, double points) {
        this.fromCustomerId = fromCustomerId;
        this.toCustomerId = toCustomerId;
        this.points = points;
    }

    public Long getFromCustomerId() {
        return fromCustomerId;
    }

    public void setFromCustomerId(Long fromCustomerId) {
        this.fromCustomerId = fromCustomerId;
    }

    public Long getToCustomerId() {
        return toCustomerId;
    }

    public void setToCustomerId(Long toCustomerId) {
        this.toCustomerId = toCustomerId;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
