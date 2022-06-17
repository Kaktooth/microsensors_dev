package com.projects.microsensors.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sensors")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Proxy(lazy = false)
public class Sensor extends Domain {

    private String name;
    private String sensorInfo;

    @Builder
    public Sensor(UUID id, String name, String sensorInfo) {
        super(id);
        this.name = name;
        this.sensorInfo = sensorInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Sensor sensor = (Sensor) o;
        return getId() != null && Objects.equals(getId(), sensor.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
