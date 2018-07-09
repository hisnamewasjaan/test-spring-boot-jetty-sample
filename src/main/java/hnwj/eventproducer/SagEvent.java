package hnwj.eventproducer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class SagEvent {


    private String sagsnummer;
    private String bidragsyder;
    private String udløbetFrist;
    private String tilstandsskift;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime localDateTime;


    public SagEvent() {
    }

    public SagEvent(String sagsnummer, String bidragsyder, String tilstandsskift) {

        this.sagsnummer = sagsnummer;
        this.bidragsyder = bidragsyder;
        this.tilstandsskift = tilstandsskift;
    }


    public void setSagsnummer(String sagsnummer) {
        this.sagsnummer = sagsnummer;
    }

    public void setBidragsyder(String bidragsyder) {
        this.bidragsyder = bidragsyder;
    }

    public void setUdløbetFrist(String udløbetFrist) {
        this.udløbetFrist = udløbetFrist;
    }

    public void setTilstandsskift(String tilstandsskift) {
        this.tilstandsskift = tilstandsskift;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    public String getSagsnummer() {
        return sagsnummer;
    }

    public String getBidragsyder() {
        return bidragsyder;
    }

    public String getUdløbetFrist() {
        return udløbetFrist;
    }

    public String getTilstandsskift() {
        return tilstandsskift;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

}
