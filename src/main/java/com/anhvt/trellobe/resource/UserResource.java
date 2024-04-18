package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.UserDTO;
import com.anhvt.trellobe.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResource {
    UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<UserDTO>> findById(@PathVariable("id") String id) {
        ServiceResult<UserDTO> result = userService.findOne(id);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @PostMapping
    public ResponseEntity<ServiceResult<UserDTO>> save(@RequestBody UserDTO userDTO) {
        ServiceResult<UserDTO> result = userService.save(userDTO);
        return new ResponseEntity<>(result, result.getStatus());
    }
}
