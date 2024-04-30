package com.projects.microsensors.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "countries")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Country extends Domain {

    private String code;
    private String name;

    @Builder
    public Country(UUID id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}