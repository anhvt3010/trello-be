package com.anhvt.trellobe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Timestamp;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // chỉ cho phép ghi
    String password;
    String username;
    String displayName;
    String avatar;
    String isActive;
    String verifyToken;
    Timestamp createdAt;
    Timestamp updatedAt;
}
