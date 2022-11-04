package nl.belastingdienst.barordersystem.Services;

import lombok.AllArgsConstructor;
import nl.belastingdienst.barordersystem.Dto.CustomerDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.*;
import nl.belastingdienst.barordersystem.Repositories.CustomerRepository;
import nl.belastingdienst.barordersystem.Repositories.DocFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@AllArgsConstructor
@Service
public class CustomerService {
    CustomerRepository customerRepository;
    DatabaseService databaseService;


    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for(Customer customer : customers){
            customerDtos.add(fromCustomer(customer));
        }
        return customerDtos;
    }
    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> CustomerOptional = customerRepository.findById(id);
        if (CustomerOptional.isEmpty()) {
            throw new RecordNotFoundException("Customer not found");
        } else {
            Customer customer = CustomerOptional.get();
            return fromCustomer(customer);
        }
    }
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

    public void deleteCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            customerRepository.delete(customerOptional.get());
        } else throw new RecordNotFoundException("Customer not found");
    }

}

