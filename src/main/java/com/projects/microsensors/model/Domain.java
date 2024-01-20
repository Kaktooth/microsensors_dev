package com.projects.microsensors.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Domain domain = (Domain) o;
        return id != null && Objects.equals(id, domain.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

