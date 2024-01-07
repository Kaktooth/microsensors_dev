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
@Table(name = "key_requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper=true)
public class KeyRequest extends Domain {

    @Builder
    public KeyRequest(UUID id, Timestamp requestDate, UUID keyId) {
        super(id);
        this.requestDate = requestDate;
        this.keyId = keyId;
    }

    private Timestamp requestDate;

    private UUID keyId;
}