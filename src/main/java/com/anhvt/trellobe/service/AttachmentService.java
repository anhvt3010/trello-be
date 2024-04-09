package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.AttachmentDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.entity.Attachment;

import java.util.List;

public interface AttachmentService {
    ServiceResult<AttachmentDTO> findOne(String id);
    ServiceResult<List<AttachmentDTO>> findByCardId(String cardId);
    ServiceResult<AttachmentDTO> save(AttachmentDTO attachmentDTO);
    ServiceResult<AttachmentDTO> update(AttachmentDTO attachmentDTO);
    void delete(String id);
}
