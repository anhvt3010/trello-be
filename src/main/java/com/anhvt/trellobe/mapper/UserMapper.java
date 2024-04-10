package com.anhvt.trellobe.mapper;

import com.anhvt.trellobe.dto.UserDTO;
import com.anhvt.trellobe.entity.User;
import com.anhvt.trellobe.util.constants.StatusUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper implements EntityMapper<User, UserDTO>{
    @Override
    public User toEntity(UserDTO dto) {
        if(dto == null) return null;
        User user = User.builder()
                .email(dto.getEmail())
                .avatar(dto.getAvatar())
                .displayName(dto.getDisplayName())
                .isActive((byte) 0)
                .verifyToken(dto.getVerifyToken())
                .username(dto.getUsername())
                .password(new BCryptPasswordEncoder(10).encode(dto.getPassword()))
                .roleIds(dto.getRoleIds())
                .build();
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        return user;
    }

    @Override
    public UserDTO toDto(User entity) {
        if(entity == null) return null;
        UserDTO userDTO = UserDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .avatar(entity.getAvatar())
                .displayName(entity.getDisplayName())
                .isActive(entity.getIsActive() == StatusUser.IS_ACTIVE.getIsActive()
                        ? StatusUser.IS_ACTIVE.getMessage()
                        : StatusUser.UN_ACTIVE.getMessage())
                .verifyToken(entity.getVerifyToken())
                .username(entity.getUsername())
                .roleIds(entity.getRoleIds())
                .build();
        userDTO.setCreatedAt(entity.getCreatedAt());
        userDTO.setUpdatedAt(entity.getUpdatedAt());
        return userDTO;
    }

    @Override
    public List<User> toEntity(List<UserDTO> dtoList) {
        if ( dtoList == null ) return null;

        List<User> list = new ArrayList<>( dtoList.size() );
        for ( UserDTO userDTO : dtoList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> toDto(List<User> entityList) {
        if ( entityList == null ) return null;

        List<UserDTO> list = new ArrayList<>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
