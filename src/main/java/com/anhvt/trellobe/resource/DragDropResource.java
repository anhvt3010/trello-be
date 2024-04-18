package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.request.DragDropColumn;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DragDropResource {@MessageMapping("/dragDrop")
    @SendTo("/topic/updates")
    public DragDropColumn handleDragDrop(DragDropColumn message) {
        // Xử lý logic khi có sự kiện kéo và thả
        System.out.println(message);
        return message;
    }
}
