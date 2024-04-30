package com.projects.microsensors.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Proxy;

import java.util.List;
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

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "sensors_types",
            joinColumns = {@JoinColumn(name = "sensor_id")},
            inverseJoinColumns = {@JoinColumn(name = "sensor_type_id")}
    )
    private List<SensorType> sensorTypes;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "sensors_placements",
            joinColumns = {@JoinColumn(name = "sensor_id")},
            inverseJoinColumns = {@JoinColumn(name = "placement_id")}
    )
    private List<Placement> placements;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    private Boolean personal;

    private UUID userId;

    @Builder
    public Sensor(UUID id, List<SensorType> sensorTypes, List<Placement> placements, Country country, Boolean personal, UUID userId) {
        super(id);
        this.sensorTypes = sensorTypes;
        this.placements = placements;
        this.country = country;
        this.personal = personal;
        this.userId = userId;
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
