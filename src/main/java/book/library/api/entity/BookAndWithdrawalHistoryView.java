package book.library.api.entity;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Immutable
@Table(name = "book_and_withdrawal_history_view")
public class BookAndWithdrawalHistoryView {

    @Id
    @Column(name = "withdrawal_id")
    private Long withdrawalId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "book_copy_id")
    private Long bookCopyId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "customer_cpf")
    private String customer_cpf;

    @Column(name = "withdraw_date")
    private LocalDateTime withdrawDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "return_limit_date")
    private LocalDate returnLimitDate;

    @Column(name = "returned_late")
    private boolean returnedLate;

}
