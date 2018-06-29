package hnwj.jetty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;

/**
 */
public class Location {

    @Id
    String id;

    double longitude;
    double latitude;
    Address address;


    public Location(double longitude, double latitude, Address address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("longitude", longitude)
                .append("latitude", latitude)
                .append("address", address)
                .toString();
    }
}
