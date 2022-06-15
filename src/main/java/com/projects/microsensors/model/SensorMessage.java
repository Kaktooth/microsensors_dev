package com.projects.microsensors.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "sensor_messages")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorMessage extends Domain {

    String message;
    Timestamp receiveDate;
    UUID sensorId;

    @Builder
    public SensorMessage(UUID id, String message, Timestamp receiveDate, UUID sensorId) {
        super(id);
        this.receiveDate = receiveDate;
        this.sensorId = sensorId;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SensorMessage that = (SensorMessage) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "[ " + receiveDate.toLocalDateTime() + " ] <-^-> " + message;
    }
}
