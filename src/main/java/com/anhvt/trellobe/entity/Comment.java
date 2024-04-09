package com.anhvt.trellobe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Builder
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_avatar")
    private String userAvatar;

    @Column(name = "user_display_name")
    private String userDisplayName;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
