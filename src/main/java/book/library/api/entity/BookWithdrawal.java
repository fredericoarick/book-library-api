package book.library.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "book_withdrawal")
public class BookWithdrawal {

    @Id
    @SequenceGenerator(name = "book_withdrawal_seq", sequenceName = "book_withdrawal_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_withdrawal_seq")
    private Long id;

    @Column(name = "withdraw_date")
    private LocalDateTime withdrawDate;

    @Column(name = "return_limit_date")
    private LocalDate returnLimitDate;

    @ManyToOne
    @JoinColumn(name = "customer_cpf")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_copy_id")
    private BookCopy bookCopy;

}
