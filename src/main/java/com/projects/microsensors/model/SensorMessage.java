package com.projects.microsensors.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@EntityListeners(CustomizedListener.class)
@NoArgsConstructor
@Table(name = "sensor_messages")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorMessage extends Domain implements Comparable<SensorMessage> {

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

    @Override
    public int compareTo(SensorMessage o) {
        return this.receiveDate.compareTo(o.receiveDate);
    }
}
