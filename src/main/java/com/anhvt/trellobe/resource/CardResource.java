package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.CardDTO;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cards")
public class CardResource {
    private final CardService cardService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<CardDTO>> findOne(@PathVariable("id") String id) {
        ServiceResult<CardDTO> result = cardService.findOne(id);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @GetMapping("/by-column/{columnId}")
    public ResponseEntity<ServiceResult<List<CardDTO>>> findByColumnId(@PathVariable("columnId") String columnId) {
        ServiceResult<List<CardDTO>> result = cardService.findByColumnId(columnId);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @PostMapping
    public ResponseEntity<ServiceResult<CardDTO>> save(@Valid @RequestBody CardDTO cardDTO) {
        ServiceResult<CardDTO> result = cardService.save(cardDTO);
        return new ResponseEntity<>(result, result.getStatus());
    }

}
