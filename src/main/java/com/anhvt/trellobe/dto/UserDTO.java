package com.anhvt.trellobe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO  implements Serializable {
    String id;

    @Email(message = "Invalid email")
    @NotNull(message = "Email cannot be blank")
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    @NotNull(message = "Username cannot be blank")
    @Size(max = 50, min = 5)
    String username;
    @NotNull(message = "Display name cannot be blank")
    @Size(max = 50, min = 3)
    String displayName;
    String avatar;
    String isActive;
    String verifyToken;
    Timestamp createdAt;
    Timestamp updatedAt;
    List<String> roleIds;
}
