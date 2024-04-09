package com.anhvt.trellobe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    String id;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "username")
    String username;

    @Column(name = "display_name")
    String displayName;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "is_active")
    byte isActive;

    @Column(name = "verify_token")
    String verifyToken;
}
