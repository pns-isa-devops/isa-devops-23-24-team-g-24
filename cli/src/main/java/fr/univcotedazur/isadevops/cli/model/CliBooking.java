package fr.univcotedazur.isadevops.cli.model;

public class CliBooking {
    private Long id;
    private long customerId;
    private long activityId;
    private boolean usePoints;

    public CliBooking(Long customerId, Long activityId, boolean usePoints) {
        this.customerId = customerId;
        this.activityId = activityId;
        this.usePoints = usePoints;
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

    public boolean getUsePoints() {
        return usePoints;
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
