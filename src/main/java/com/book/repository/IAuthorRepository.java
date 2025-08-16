package com.book.repository;

import com.book.model.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IAuthorRepository extends JpaRepository<AuthorModel,Long> {
    Optional<AuthorModel> findByNameAndBirthYearAndDeathYear(
            String name, Integer birthYear, Integer deathYear);
}
