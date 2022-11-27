package book.library.api.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequest {

    private String title;
    private String author;
    private LocalDate publicationDate;

}
