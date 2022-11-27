package book.library.api.repository;

import book.library.api.entity.BookAndWithdrawalHistoryView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAndWithdrawalHistoryViewRepository extends CrudRepository<BookAndWithdrawalHistoryView, Long> {

    @Query("select v from BookAndWithdrawalHistoryView v where v.customer_cpf = ?1")
    List<BookAndWithdrawalHistoryView> findAllByCustomer_Cpf(String cpf);

}
