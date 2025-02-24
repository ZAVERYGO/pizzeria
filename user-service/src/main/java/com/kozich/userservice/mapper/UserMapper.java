package com.kozich.userservice.mapper;

import com.kozich.userservice.core.dto.UserDTO;
import com.kozich.userservice.entity.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(target = "dtCreate", qualifiedByName = "mapLongToLocalDateTime", source = "dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLongToLocalDateTime", source = "dtUpdate")
    UserEntity userDTOToUserEntity(UserDTO userDTO);

    @Named("mapLongToLocalDateTime")
    default LocalDateTime mapLongToLocalDateTime(Long dateTime) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTime), ZoneId.systemDefault());
    }


    @Mapping(target = "dtCreate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtUpdate")
    UserDTO userEntityToUserDTO(UserEntity userEntity);

    @Named("mapLocalDateTimeToLong")
    default Long mapLocalDateTimeToLong(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

}
