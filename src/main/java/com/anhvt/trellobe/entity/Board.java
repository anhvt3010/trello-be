package com.anhvt.trellobe.entity;

import com.anhvt.trellobe.util.converter.JsonToStringListConverter;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Builder
@Table(name = "board")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "title")
    @NotEmpty
    @Size(max = 255)
    private String title;

    @Column(name = "type")
    @NotEmpty
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_ids")
    @Convert(converter = JsonToStringListConverter.class)
    private List<String> ownerIds;

    @Column(name = "member_ids")
    @Convert(converter = JsonToStringListConverter.class)
    private List<String> memberIds;

    @Column(name = "column_order_ids")
    @Convert(converter = JsonToStringListConverter.class)
    private List<String> columnOrderIds;

    @Column(name = "status")
    private Boolean status;
}
