package book.library.api.service;

import book.library.api.entity.BookAndWithdrawalHistoryView;
import book.library.api.entity.BookAndWithdrawalView;
import book.library.api.entity.BookWithdrawal;
import book.library.api.repository.BookAndWithdrawalHistoryViewRepository;
import book.library.api.repository.BookAndWithdrawalViewRepository;
import book.library.api.repository.BookWithdrawalRepository;
import book.library.api.request.BookWithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookWithdrawalService {

    private static final long STANDARD_RETURN_LIMIT_WEEKS = 2;

    private final BookWithdrawalRepository bookWithdrawalRepository;
    private final BookAndWithdrawalViewRepository bookAndWithdrawalViewRepository;
    private final BookAndWithdrawalHistoryViewRepository bookAndWithdrawalHistoryViewRepository;
    private final CustomerService customerService;
    private final BookService bookService;

    @Transactional
    public BookWithdrawal withdrawBook(BookWithdrawRequest request) {
        var customer = customerService.findCustomerByCpf(request.getCustomerCpf());
        var bookCopy = bookService.findCopyById(request.getBookCopyId());

        if (!bookCopy.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "book.copy.not.available");
        }

        var bookWithdrawal = new BookWithdrawal();
        bookWithdrawal.setCustomer(customer);
        bookWithdrawal.setBookCopy(bookCopy);
        bookWithdrawal.setWithdrawDate(LocalDateTime.now());
        bookWithdrawal.setReturnLimitDate(LocalDateTime.now().toLocalDate().plusWeeks(STANDARD_RETURN_LIMIT_WEEKS));
        return bookWithdrawalRepository.save(bookWithdrawal);
    }

    @Transactional
    public BookWithdrawal renewBookWithdrawal(Long bookWithdrawalId) {
        var withdrawal = findWithdrawalById(bookWithdrawalId);

        if (withdrawal.getReturnLimitDate().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "book.return.already.late");
        }

        var newReturnLimitDate = withdrawal.getReturnLimitDate().plusWeeks(STANDARD_RETURN_LIMIT_WEEKS);
        withdrawal.setReturnLimitDate(newReturnLimitDate);
        return bookWithdrawalRepository.save(withdrawal);
    }

    @Transactional
    public void returnBook(Long bookWithdrawalId) {
        var withdrawal = findWithdrawalById(bookWithdrawalId);
        bookWithdrawalRepository.delete(withdrawal);
    }

    public BookWithdrawal findWithdrawalById(Long bookWithdrawalId) {
        return bookWithdrawalRepository.findById(bookWithdrawalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "withdrawal.not.found"));
    }


    public List<BookAndWithdrawalView> findAllActiveWithdrawals() {
        return bookAndWithdrawalViewRepository.findAll();
    }
    public List<BookAndWithdrawalView> findAllActiveWithdrawalsByCpf(String cpf) {
        return bookAndWithdrawalViewRepository.findAllByCustomer_Cpf(cpf);
    }

    public List<BookAndWithdrawalHistoryView> findAllReturnedWithdrawalsByCpf(String cpf) {
        return bookAndWithdrawalHistoryViewRepository.findAllByCustomer_Cpf(cpf);
    }

}
