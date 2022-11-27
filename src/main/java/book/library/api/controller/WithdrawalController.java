package book.library.api.controller;

import book.library.api.entity.BookAndWithdrawalHistoryView;
import book.library.api.entity.BookAndWithdrawalView;
import book.library.api.entity.BookWithdrawal;
import book.library.api.request.BookWithdrawRequest;
import book.library.api.service.BookWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "withdrawal")
@RequiredArgsConstructor
public class WithdrawalController {

    private final BookWithdrawalService bookWithdrawalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookWithdrawal withdrawBook(@RequestBody BookWithdrawRequest request) {
        return bookWithdrawalService.withdrawBook(request);
    }

    @PutMapping("/{withdrawalId}")
    @ResponseStatus(HttpStatus.OK)
    public BookWithdrawal renewBookWithdrawal(@PathVariable("withdrawalId") Long withdrawalId) {
        return bookWithdrawalService.renewBookWithdrawal(withdrawalId);
    }

    @DeleteMapping("/{withdrawalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnBook(@PathVariable("withdrawalId") Long withdrawalId) {
        bookWithdrawalService.returnBook(withdrawalId);
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<BookAndWithdrawalView> findAllActiveWithdrawalsByCpf(@RequestParam(value = "cpf", required = false) String cpf) {
        if (cpf != null) {
            return bookWithdrawalService.findAllActiveWithdrawalsByCpf(cpf);
        }
        return bookWithdrawalService.findAllActiveWithdrawals();
    }

    @GetMapping("/returned")
    @ResponseStatus(HttpStatus.OK)
    public List<BookAndWithdrawalHistoryView> findAllReturnedWithdrawalsByCpf(@RequestParam("cpf") String cpf) {
        return bookWithdrawalService.findAllReturnedWithdrawalsByCpf(cpf);
    }

}
