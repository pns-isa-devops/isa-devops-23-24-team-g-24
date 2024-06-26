package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.interfaces.*;
import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.entities.Partner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


@Service
public class StatsRetriever implements StatsCustomer, StatsPartner, StatsActivity{

    private final CustomerFinder customerFinder;
    private final PartnerFinder partnerFinder;
    private final BookingFinder bookingFinder;
    private final ActivityFinder activityFinder;

    @Autowired
    public StatsRetriever(CustomerFinder customerFinder, PartnerFinder partnerFinder, BookingFinder bookingFinder, ActivityFinder activityFinder) {
        this.customerFinder = customerFinder;
        this.partnerFinder = partnerFinder;
        this.bookingFinder = bookingFinder;
        this.activityFinder = activityFinder;
    }

    
    @Override
    @Transactional(readOnly = true)
    public String retrieveStatsCustomer(long idCustomer) {

        String result = "";

        List<Booking> bookings = bookingFinder.findAllBookings();
        List<Booking> bookingsCustomer = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomer().getId() == idCustomer) {
                bookingsCustomer.add(booking);
            }
        }

        Optional<Customer> customer = customerFinder.findById((long) idCustomer);
        if (customer.isPresent()) {
            result +="Customer " + customer.get().getName() + " has made " + bookingsCustomer.size() + " bookings" + "\n";
            for (Booking booking : bookingsCustomer) {
                result += "Booking " + booking.getId() + " for activity " + booking.getActivity().getName() + "\n";
            }
            return result;
        } else {
            return "Customer not found";
        }
    }

    @Override
    public String retrieveStatsPartner(long idPartner) {
        String result = "";

        List<Booking> bookings = bookingFinder.findAllBookings();

        Optional<Partner> partner = partnerFinder.findById((long) idPartner);
        if (partner.isPresent()) {
            result += "Partner " + partner.get().getName() + "\n" + "Location: " + partner.get().getLocation() + "\n" + "Description: " + partner.get().getDescription() + "\n";
        } else {
            return "Partner not found";
        }

        List<Activity> activities = activityFinder.findAllActivities();
        List<Activity> activitiesPartner = new ArrayList<>();
        result += "Activities associated to this partner: \n";
        for(Activity activity : activities) {
            if (activity.getPartner().getId() == idPartner) {
                result += "Activity " + activity.getName() + "\n";
                activitiesPartner.add(activity);
            }
        }

        int count = 0;
        for(Activity activity: activitiesPartner) {
            for(Booking booking : bookings) {
                if (booking.getActivity().getId() == activity.getId()) {
                    count++;
                }
            }
        }

        result += "Total number of bookings for this partner: " + count + "\n";
        return result;


    }


    @Override
    public String retrieveActivityStats(long idActivity) {
        Optional<Activity> activity = activityFinder.findById(idActivity);
        String result = "";
        if (activity.isPresent()) {
            result += "Activity " + activity.get().getName() + "\n" + "Location: " + activity.get().getLocation() + "\n" + "Number of places: " + activity.get().getNumberOfPlaces() + "\n";
        } else {
            return "Activity not found";
        }

        List<Booking> bookings = bookingFinder.findAllBookings();
        for(Booking booking : bookings) {
            if (booking.getActivity().getId() == idActivity) {
                result += "Booking " + booking.getId() + " for customer " + booking.getCustomer().getName() + "\n";
            }
        }

        return result;
    }
}
