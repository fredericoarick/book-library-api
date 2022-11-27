package book.library.api.service;

import book.library.api.entity.Book;
import book.library.api.entity.BookCopy;
import book.library.api.repository.BookCopyRepository;
import book.library.api.repository.BookRepository;
import book.library.api.request.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    @Transactional
    public Book insertBook(BookRequest request) {
        var book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publicationDate(request.getPublicationDate())
                .build();
        return bookRepository.save(book);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book.not.found"));
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public BookCopy addBookCopy(Long bookId) {
        var book = findBookById(bookId);
        var newCopy = new BookCopy();
        newCopy.setBook(book);
        newCopy.setAvailable(true);
        return bookCopyRepository.save(newCopy);
    }

    public BookCopy findCopyById(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book.copy.not.found"));
    }

}
