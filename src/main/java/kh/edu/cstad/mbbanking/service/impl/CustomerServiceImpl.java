package kh.edu.cstad.mbbanking.service.impl;

import kh.edu.cstad.mbbanking.domain.Customer;
import kh.edu.cstad.mbbanking.dto.CreateCustomerRequest;
import kh.edu.cstad.mbbanking.dto.CustomerResponse;
import kh.edu.cstad.mbbanking.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbbanking.mapper.CustomerMapper;
import kh.edu.cstad.mbbanking.repository.CustomerRepository;
import kh.edu.cstad.mbbanking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Phone Number not found")
        );

        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        customer = customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Phone Number not found"));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toCustomerResponse).toList();
    }

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }

        Customer customer = customerMapper.fromCustomerRequest(createCustomerRequest);

        customer.setIsDeleted(false);

        log.info("Customer ID before save: " + customer.getId());
        customer = customerRepository.save(customer);
        log.info("Customer ID After save: " + customer.getId());

        return customerMapper.toCustomerResponse(customer);
    }
}
