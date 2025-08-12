package kh.com.csx.service.impl;

import kh.com.csx.dto.CustomerRequest;
import kh.com.csx.dto.CustomerResponse;
import kh.com.csx.entity.Customer;
import kh.com.csx.repository.CustomerRepository;
import kh.com.csx.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    @Override
    public CustomerRequest create(CustomerRequest customerRequest) {
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        if (customerRequest.getProfilePicture() == null) {
            customer.setProfilePicture(""); // or a default image URL
        } else {
            customer.setProfilePicture(customerRequest.getProfilePicture());
        }
        customer.setCreatedAt(new Date());
        Customer newCustomer = customerRepository.save(customer);
        CustomerRequest response = modelMapper.map(newCustomer, CustomerRequest.class);
        response.setId(newCustomer.getId()); // Ensure id is set in response
        return response;
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest, Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            log.error("Customer Id {} not found", id);
            return new CustomerResponse();
        }
        Customer customerToUpdate = customer.get();
        customerToUpdate.setUsername(customerRequest.getUsername());
        customerToUpdate.setPhoneNumber(customerRequest.getPhoneNumber());
        customerToUpdate.setAddress(customerRequest.getAddress());
        customerToUpdate.setTelegramId(customerRequest.getTelegramId());
        customerToUpdate.setTelegramUsername(customerRequest.getTelegramUsername());
        customerToUpdate.setProfilePicture(customerRequest.getProfilePicture());
        customerToUpdate.setUpdatedAt(new Date());

        customerRepository.saveAndFlush(customerToUpdate);

        CustomerResponse response = new CustomerResponse();
        response.setId(customerToUpdate.getId());
        response.setUsername(customerToUpdate.getUsername());
        response.setPhoneNumber(customerToUpdate.getPhoneNumber());
        response.setAddress(customerToUpdate.getAddress());
        response.setTelegramId(customerToUpdate.getTelegramId());
        response.setTelegramUsername(customerToUpdate.getTelegramUsername());
        return response;
    }

    @Override
    public void delete(Long id) {
//        Customer customer = this.getCustomer(id);
        customerRepository.deleteById(id);
//        customer.setCreatedAt(new Date());

    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            log.error("No customers found");
            return List.of();
        } else {
            return customers.stream().map(customer -> {
                CustomerResponse response = new CustomerResponse();
                response.setId(customer.getId());
                response.setUsername(customer.getUsername());
                response.setPhoneNumber(customer.getPhoneNumber());
                response.setAddress(customer.getAddress());
                response.setTelegramId(customer.getTelegramId());
                response.setTelegramUsername(customer.getTelegramUsername());
                return response;
            }).toList();
        }
    }

    @Override
    public CustomerResponse findById(Long id) {
        CustomerResponse customerResponse = new CustomerResponse();

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            log.error("Customer with phone number {} not found", id);
            return customerResponse;
        } else {
            Customer foundCustomer = customer.get();
            customerResponse.setId(foundCustomer.getId());
            customerResponse.setUsername(foundCustomer.getUsername());
            customerResponse.setPhoneNumber(foundCustomer.getPhoneNumber());
            customerResponse.setAddress(foundCustomer.getAddress());
            customerResponse.setTelegramId(foundCustomer.getTelegramId());
            customerResponse.setTelegramUsername(foundCustomer.getTelegramUsername());
        }

        return customerResponse;
    }
}
