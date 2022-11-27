package book.library.api.repository;

import book.library.api.entity.BookWithdrawal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookWithdrawalRepository extends CrudRepository<BookWithdrawal, Long> {


}
