package com.projects.microsensors.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "Username should not be empty")
    @Size(min = 4, max = 20, message = "Username must contain at least 4 characters")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, max = 20, message = "The password must contain at least 8 characters")
    private String password;
}
