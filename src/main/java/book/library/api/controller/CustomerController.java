package book.library.api.controller;

import book.library.api.entity.Customer;
import book.library.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer insertCustomer(@RequestBody Customer customer) {
        return customerService.insertCustomer(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> listCustomers() {
        return customerService.listAllCustomers();
    }

    @GetMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Customer findCustomerByCpf(@PathVariable("cpf") String cpf) {
        return customerService.findCustomerByCpf(cpf);
    }

}
