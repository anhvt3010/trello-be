package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.AttachmentDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/attachments")
public class AttachmentResource {
    private final AttachmentService attachmentService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<AttachmentDTO>> findOne(@PathVariable("id") String id){
        ServiceResult<AttachmentDTO> result = attachmentService.findOne(id);
        return new ResponseEntity<>(result, result.getStatus());
    }
    @GetMapping("/by-card/{id}")
    public ResponseEntity<ServiceResult<List<AttachmentDTO>>> findByCardId(@PathVariable("id") String id){
        ServiceResult<List<AttachmentDTO>> result = attachmentService.findByCardId(id);
        return new ResponseEntity<>(result, result.getStatus());
    }
}
