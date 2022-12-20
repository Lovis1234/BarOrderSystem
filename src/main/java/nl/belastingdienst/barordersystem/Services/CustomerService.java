package nl.belastingdienst.barordersystem.Services;

import lombok.AllArgsConstructor;
import nl.belastingdienst.barordersystem.Dto.CustomerDto;
import nl.belastingdienst.barordersystem.Dto.CustomerGetDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Customer;
import nl.belastingdienst.barordersystem.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {
    CustomerRepository customerRepository;
    DatabaseService databaseService;


    public List<CustomerGetDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerGetDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(toCustomerGetDto(customer));
        }
        return customerDtos;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = toCustomer(customerDto);
        Customer newCustomer = customerRepository.save(customer);
        CustomerDto dto = fromCustomer(newCustomer);
        return dto;

    }


    private CustomerDto fromCustomer(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setInvoices(customer.getInvoices());
        return customerDto;
    }

    private CustomerGetDto toCustomerGetDto(Customer customer) {
        CustomerGetDto customerDto = new CustomerGetDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        return customerDto;
    }

    private Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customer.getId());
        customer.setName(customerDto.getName());
        customer.setInvoices(customerDto.getInvoices());
        return customer;
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            customerRepository.delete(customerOptional.get());
        } else throw new RecordNotFoundException("Customer not found");
    }

    public void updateCustomer(Long id, CustomerDto dto) {
        if (customerRepository.findById(id).isEmpty()) throw new RecordNotFoundException("Customer not found");
        Customer customer = customerRepository.findById(id).get();
        customer.setName(dto.getName());
        customer.setId(dto.getId());
        customer.setInvoices(dto.getInvoices());
        customerRepository.save(customer);
    }
}

