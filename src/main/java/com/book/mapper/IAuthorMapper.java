package com.book.mapper;

import com.book.dto.response.AuthorResponse;
import com.book.model.AuthorModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuthorMapper {
    AuthorModel toModel(AuthorResponse response);
    AuthorResponse toDTO(AuthorModel model);
}
