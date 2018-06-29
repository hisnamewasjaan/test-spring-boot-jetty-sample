package hnwj.jetty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 */
@Document


public class Job {

    @Id
    private String id;

    public String name;
    String description;

    private Instant when;

    JobProvider jobProvider;
    Location location;
    Frequency frequency;
    Payment payment;

    public Job() {
    }

    public Job(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Job(String name, String description, JobProvider jobProvider) {
        this.name = name;
        this.description = description;
        this.jobProvider = jobProvider;
    }


    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("when", when)
                .append("jobProvider", jobProvider)
                .append("location", location)
                .append("frequency", frequency)
                .append("payment", payment)
                .toString();
    }
}
