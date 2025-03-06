package com.kozich.productservice.controller.mapper;

import com.kozich.productservice.core.dto.CategoryDTO;
import com.kozich.productservice.core.dto.ProductDTO;
import com.kozich.productservice.entity.CategoryEntity;
import com.kozich.productservice.entity.ProductEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "categoryId.uuid")
    @Mapping(target = "dtCreate", qualifiedByName = "mapLongToLocalDateTime", source = "dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLongToLocalDateTime", source = "dtUpdate")
    ProductEntity productDTOToProductEntity(ProductDTO productDTO);

    @Named("mapLongToLocalDateTime")
    default LocalDateTime mapLongToLocalDateTime(Long dateTime) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTime), ZoneId.systemDefault());
    }


    @Mapping(source = "categoryId.uuid", target = "categoryId")
    @Mapping(target = "dtCreate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtUpdate")
    ProductDTO productEntityToProductDTO(ProductEntity productEntity);

    @Named("mapLocalDateTimeToLong")
    default Long mapLocalDateTimeToLong(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}
