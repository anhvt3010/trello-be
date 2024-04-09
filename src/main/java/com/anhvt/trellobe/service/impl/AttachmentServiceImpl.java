package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.dto.AttachmentDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.entity.Attachment;
import com.anhvt.trellobe.repository.AttachmentRepository;
import com.anhvt.trellobe.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    private final AttachmentRepository attachmentRepository;
    private final ModelMapper mapper;

    @Override
    public ServiceResult<AttachmentDTO> findOne(String id) {
        log.debug("Request to get Attachment : {}", id);
        ServiceResult<AttachmentDTO> result = new ServiceResult<>();
        try {
            Optional<Attachment> attachment = attachmentRepository.findById(id);
            if(attachment.isPresent()){
                result.setData(mapper.map(attachment, AttachmentDTO.class));
                result.setStatus(HttpStatus.OK);
            } else {
                result.setMessage("attachment.id.not_found");
                result.setStatus(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            result.setMessage("attachment.id.bad_request");
            result.setStatus(HttpStatus.BAD_REQUEST);
        }
        return result;
    }
    @Override
    public ServiceResult<List<AttachmentDTO>> findByCardId(String cardId) {
        log.debug("Request to get Attachment by cardId : {}", cardId);
        return null;
    }

    @Override
    public ServiceResult<AttachmentDTO> save(AttachmentDTO attachmentDTO) {
        log.debug("Request to save Attachment : {}", attachmentDTO);
        ServiceResult<AttachmentDTO> result = new ServiceResult<>();
        try {
            Attachment attachment = attachmentRepository.save(mapper.map(attachmentDTO, Attachment.class));
            result.setData(mapper.map(attachment, AttachmentDTO.class));
            result.setStatus(HttpStatus.CREATED);
            result.setMessage("attachment.create.success");
        } catch (Exception e){
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("attachment.create.failed");
        }
        return result;
    }

    @Override
    public ServiceResult<AttachmentDTO> update(AttachmentDTO attachmentDTO) {
        log.debug("Request to update Attachment : {}", attachmentDTO);

        return null;
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Attachment : {}", id);
        attachmentRepository.deleteById(id);
    }
}
