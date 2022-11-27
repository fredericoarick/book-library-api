package book.library.api.repository;

import book.library.api.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

    Optional<Customer> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    List<Customer> findAll();

}
