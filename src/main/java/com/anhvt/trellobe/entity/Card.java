package com.anhvt.trellobe.entity;

import com.anhvt.trellobe.util.converter.JsonToStringListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "card")
@EqualsAndHashCode(callSuper = true)
public class Card extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "column_id")
    private String columnId;

    @Column(name = "attachment_ids")
    @Convert(converter = JsonToStringListConverter.class)
    private List<String> attachmentIds;

    @Column(name = "comment_ids")
    @Convert(converter = JsonToStringListConverter.class)
    private List<String> commentIds;

    @Column(name = "title")
    @Size(max = 255)
    private String title;

    @Column(name = "cover")
    private String cover;

    @Column(name = "description")
    private String description;

    @Column(name = "member_ids")
    @Convert(converter = JsonToStringListConverter.class)
    private List<String> memberIds;

    @Column(name = "status")
    private Boolean status;

}
