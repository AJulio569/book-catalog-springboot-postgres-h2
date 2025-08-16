package com.book.service;

import com.book.client.GutendexClient;
import com.book.dto.response.AuthorResponse;
import com.book.dto.response.BookResponse;
import com.book.dto.response.PaginatedBookResponse;
import com.book.mapper.IAuthorMapper;
import com.book.mapper.IBookMapper;
import com.book.model.AuthorModel;
import com.book.model.BookModel;
import com.book.repository.IAuthorRepository;
import com.book.repository.IBookRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final IBookMapper mapper;
    private final IAuthorMapper authorMapper;
    private final GutendexClient gutendexClient;
    private final IAuthorRepository authorRepository;
    private final IBookRepository bookRepository;

    public PaginatedBookResponse getBookByTitle(String title){
           PaginatedBookResponse apiResponse = gutendexClient.searchBooks(title);
           return buildPaginatedResponse(apiResponse.getResults(),apiResponse);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(mapper::toDTO).toList();
      }

    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByLanguage(String languageCode) {
        return bookRepository.findByLanguagesContaining(languageCode)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getTop2DownloadedBooks() {
        return bookRepository.findTop2ByOrderByDownloadCountDesc()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByAuthorName(String authorName) {
        return bookRepository.findByAuthors_NameContainingIgnoreCase(authorName)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getBooksBeforeYear(Integer year) {
        return bookRepository.findByAuthors_BirthYearBefore(year)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchBooksByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public BookResponse saveBookFromGutendex(String title) {
        PaginatedBookResponse apiResponse = gutendexClient.searchBooks(title);
        if (apiResponse == null || apiResponse.getResults() == null || apiResponse.getResults().isEmpty()) {
            return null;
        }

        BookResponse bookResponse = apiResponse.getResults().get(0);

        Optional<BookModel> existingBook = bookRepository.findByGutendexId(bookResponse.getId());
        if (existingBook.isPresent()) {
            System.out.println("Libro ya existe en la base de datos: " + title);
            BookResponse dto = mapper.toDTO(existingBook.get());
            dto.setAlreadyExists(true);
            return dto;
        }

        BookModel bookModel = setBook(bookResponse);
        bookModel.setAuthors(new ArrayList<>());

        for (AuthorResponse author : bookResponse.getAuthors()) {
            AuthorModel authorModel = authorRepository
                    .findByNameAndBirthYearAndDeathYear(
                            author.getName(),
                            author.getBirthYear(),
                            author.getDeathYear())
                    .orElseGet(() -> {
                        AuthorModel newAuthor = authorMapper.toModel(author);
                        return authorRepository.save(newAuthor);
                    });
              bookModel.getAuthors().add(authorModel);
        }

        BookModel savedBook = bookRepository.save(bookModel);
        BookResponse savedDto = mapper.toDTO(savedBook);
        savedDto.setAlreadyExists(false); // ✅ Marcamos que es nuevo
        return savedDto;
    }

    private PaginatedBookResponse buildPaginatedResponse(List<BookResponse> rawResults, PaginatedBookResponse originalMeta) {
        PaginatedBookResponse paginated = new PaginatedBookResponse();
        paginated.setCount(originalMeta.getCount());
        paginated.setNext(originalMeta.getNext());
        paginated.setPrevious(originalMeta.getPrevious());
        paginated.setResults(rawResults);
        return paginated;
    }

    private BookModel setBook(BookResponse response) {
        BookModel bookModel = new BookModel();
        bookModel.setGutendexId(response.getId());
        bookModel.setTitle(response.getTitle());
        bookModel.setSummaries(response.getSummaries());
        bookModel.setSubjects(response.getSubjects());
        bookModel.setBookshelves(response.getBookshelves());
        bookModel.setLanguages(response.getLanguages());
        bookModel.setCopyright(response.getCopyright());
        bookModel.setMediaType(response.getMediaType());
        bookModel.setFormats(response.getFormats());
        bookModel.setDownloadCount(response.getDownloadCount());
        return bookModel;
    }
}
