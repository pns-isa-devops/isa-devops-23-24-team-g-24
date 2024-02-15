package fr.univcotedazur.isadevops.cli.model;

public class CliBooking {
    private Long id;
    private Long customerId;
    private Long activityId;

    public CliBooking(Long id, Long customerId, Long activityId) {
        this.id = id;
        this.customerId = customerId;
        this.activityId = activityId;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getActivityId() {
        return activityId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }
}
