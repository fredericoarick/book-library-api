package book.library.api.service;

import book.library.api.entity.Customer;
import book.library.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public Customer insertCustomer(Customer customer) {
        if (repository.existsById(customer.getCpf())) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer.already.exists");
        }
        return repository.save(customer);
    }

    public List<Customer> listAllCustomers() {
        return repository.findAll();
    }

    public Customer findCustomerByCpf(String cpf) {
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer.not.found"));
    }

}
