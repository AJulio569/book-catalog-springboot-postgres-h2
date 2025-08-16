package com.book.repository;

import com.book.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IBookRepository extends JpaRepository<BookModel,Long> {
    Optional<BookModel> findByGutendexId(Long gutendexId);

    List<BookModel> findByLanguagesContaining(String languageCode);

    List<BookModel> findTop2ByOrderByDownloadCountDesc();

    List<BookModel> findByAuthors_NameContainingIgnoreCase(String authorName);

    List<BookModel> findByAuthors_BirthYearBefore(Integer year);

    List<BookModel> findByTitleContainingIgnoreCase(String keyword);
}
