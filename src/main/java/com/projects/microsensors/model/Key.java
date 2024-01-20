package com.projects.microsensors.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "keys")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Key extends Domain {

    @Builder
    public Key(UUID id, Timestamp creationDate, UUID key, UUID userId, String ip) {
        super(id);
        this.creationDate = creationDate;
        this.key = key;
        this.userId = userId;
        this.ip = ip;
    }

    private Timestamp creationDate;

    private UUID key;

    private UUID userId;

    private String ip;
}