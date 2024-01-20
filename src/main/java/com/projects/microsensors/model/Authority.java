package com.projects.microsensors.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authorities")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Authority extends Domain {


    private String username;

    private Integer authority;

    private UUID userId;

    @Builder
    public Authority(UUID id, String username, Integer authority, UUID userId) {
        super(id);
        this.username = username;
        this.authority = authority;
        this.userId = userId;
    }
}