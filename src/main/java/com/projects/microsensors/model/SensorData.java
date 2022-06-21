package com.projects.microsensors.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sensors_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorData extends Domain implements Comparable<SensorData> {

    Timestamp receiveDate;
    byte[] data;
    UUID sensorId;

    @Builder
    public SensorData(UUID id, Timestamp receiveDate, byte[] data, UUID sensorId) {
        super(id);
        this.receiveDate = receiveDate;
        this.data = data;
        this.sensorId = sensorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SensorData that = (SensorData) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        System.out.println(data);
        return " \uD83C\uDD83\uD83C\uDD78\uD83C\uDD7C\uD83C\uDD74: " + receiveDate.toLocalDateTime() + " \n" + Arrays.toString(data) + "\n";
    }

    @Override
    public int compareTo(SensorData o) {
        return this.receiveDate.compareTo(o.receiveDate);
    }
}
