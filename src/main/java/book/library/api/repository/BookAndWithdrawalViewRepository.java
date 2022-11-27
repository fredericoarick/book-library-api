package book.library.api.repository;

import book.library.api.entity.BookAndWithdrawalView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAndWithdrawalViewRepository extends CrudRepository<BookAndWithdrawalView, Long> {

    List<BookAndWithdrawalView> findAll();

    @Query("""
        select v 
        from BookAndWithdrawalView v 
        where v.customer_cpf = ?1
    """)
    List<BookAndWithdrawalView> findAllByCustomer_Cpf(String cpf);

}
