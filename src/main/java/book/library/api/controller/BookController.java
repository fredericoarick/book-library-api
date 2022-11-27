package book.library.api.controller;

import book.library.api.entity.Book;
import book.library.api.entity.BookCopy;
import book.library.api.request.BookRequest;
import book.library.api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book insertBook(@RequestBody BookRequest bookRequest) {
        return bookService.insertBook(bookRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> listCustomers() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book findBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id);
    }

    @PostMapping("/{id}/copy")
    @ResponseStatus(HttpStatus.CREATED)
    public BookCopy addBookCopy(@PathVariable("id") Long id) {
        return bookService.addBookCopy(id);
    }

}
