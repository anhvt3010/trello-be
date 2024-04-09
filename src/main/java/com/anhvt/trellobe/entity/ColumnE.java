package com.anhvt.trellobe.entity;


import com.anhvt.trellobe.util.converter.JsonToStringListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Builder
@Table(name = "bcolumn")
@EqualsAndHashCode(callSuper = true)
public class ColumnE extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "board_id")
    private String boardId;

    @Column(name = "title")
    private String title;

    @Column(name = "card_order_ids")
    @Convert(converter = JsonToStringListConverter.class)
    private List<String> cardOrderIds;

    @Column(name = "status")
    private Boolean status;

}
