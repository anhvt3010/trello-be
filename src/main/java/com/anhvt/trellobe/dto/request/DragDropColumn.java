package com.anhvt.trellobe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DragDropColumn {
    String id;
    String[] columnOrderIds;
}
