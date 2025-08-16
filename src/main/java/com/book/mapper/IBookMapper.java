package com.book.mapper;

import com.book.dto.response.BookResponse;
import com.book.model.BookModel;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IBookMapper {
    BookModel toModel(BookResponse response);
    BookResponse toDTO(BookModel model);

    List<BookResponse> toDTOList(List<BookModel> books);
    List<BookModel> toModelList(List<BookResponse> gutendexResponses);

    default String getImageUrl(BookModel book) {
        if (book.getFormats() != null) {
            return book.getFormats().get("image/jpeg");
        }
        return null;
    }
}
