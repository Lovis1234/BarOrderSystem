package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.CustomerDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Customer;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for(Customer customer : customers){
            customerDtos.add(fromCustomer(customer));
        }
        return customerDtos;
    }
//    public CustomerDto getCustomerById(Long id) {
//        Optional<Customer> CustomerOptional = customerRepository.findById(id);
//        if (!CustomerOptional.isPresent()) {
//            throw new RecordNotFoundException("Customer not found");
//        } else {
//            Customer customer = CustomerOptional.get();
//            CustomerDto customerDto = fromCustomer(customer);
//            return customerDto;
//        }
//    }
public void preload(Customer customer)
        throws IOException
{
    customerRepository.save(customer);
}


    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = toCustomer(customerDto);
        Customer newCustomer = customerRepository.save(customer);
        CustomerDto dto = fromCustomer(newCustomer);
        return dto;

    }


    private CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setInvoices(customer.getInvoices());
        return customerDto;
    }

    private Customer toCustomer(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setId(customer.getId());
        customer.setName(customerDto.getName());
        customer.setInvoices(customerDto.getInvoices());
        return customer;
    }
}

