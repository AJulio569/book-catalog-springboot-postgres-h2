package com.book.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gutendex_id",unique = true, nullable = false)
    private Long gutendexId;

    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<AuthorModel> authors = new ArrayList<>();

    @Column(length = 2000)
    private List<String> summaries;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_translators",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "translator_id")
    )
    private List<TranslatorModel> translators;

    @ElementCollection
    @CollectionTable(name = "book_subjects", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "subject")
    private List<String> subjects;

    @ElementCollection
    @CollectionTable(name = "book_bookshelves", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "bookshelf")
    private List<String> bookshelves;

    @ElementCollection
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    private List<String> languages;

    private Boolean copyright;

    @Column(name = "media_type")
    private String mediaType;

    @ElementCollection
    @CollectionTable(name = "book_formats", joinColumns = @JoinColumn(name = "book_id"))
    @MapKeyColumn(name = "format_type")
    @Column(name = "url")
    private Map<String, String> formats;

    @Column(name = "download_count")
    private Integer downloadCount;
}
