package com.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedBookResponse {
    private Long count;
    private String next;
    private String previous;
    private List<BookResponse> results;
}
