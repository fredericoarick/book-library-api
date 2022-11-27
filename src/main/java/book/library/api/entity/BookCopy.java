package book.library.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_copy")
public class BookCopy {

    @Id
    @SequenceGenerator(name = "book_copy_seq", sequenceName = "book_copy_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_copy_seq")
    private Long id;

    @Column(name = "available")
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

}
