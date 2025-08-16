package com.book.mapper;

import com.book.dto.response.TranslatorResponse;
import com.book.model.TranslatorModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITranslatorMapper {
    TranslatorModel toEntity(TranslatorResponse dto);
    TranslatorResponse toDto(TranslatorModel model);
}
