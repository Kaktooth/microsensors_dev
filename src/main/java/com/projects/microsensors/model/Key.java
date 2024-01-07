package com.projects.microsensors.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "keys")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper=true)
public class Key extends Domain {

    @Builder
    public Key(UUID id, Timestamp creationDate, UUID key, String ip) {
        super(id);
        this.creationDate = creationDate;
        this.key = key;
        this.ip = ip;
    }

    private Timestamp creationDate;

    private UUID key;

    private String ip;
}