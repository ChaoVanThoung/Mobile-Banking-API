package kh.edu.cstad.mbbanking.service.impl;

import kh.edu.cstad.mbbanking.domain.Customer;
import kh.edu.cstad.mbbanking.domain.CustomerSegment;
import kh.edu.cstad.mbbanking.domain.KYC;
import kh.edu.cstad.mbbanking.dto.CreateCustomerRequest;
import kh.edu.cstad.mbbanking.dto.CustomerResponse;
import kh.edu.cstad.mbbanking.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbbanking.mapper.CustomerMapper;
import kh.edu.cstad.mbbanking.repository.CustomerRepository;
import kh.edu.cstad.mbbanking.repository.CustomerSegmentRepository;
import kh.edu.cstad.mbbanking.repository.KYCRepository;
import kh.edu.cstad.mbbanking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final KYCRepository kycRepository;
    private final CustomerSegmentRepository customerSegmentRepository;

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
        return customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
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

        if (kycRepository.existsByNationalCardId(createCustomerRequest.nationalCardId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National card id already exists");
        }

        CustomerSegment customerSegment = customerSegmentRepository.findBySegment(createCustomerRequest.segment())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found"));
        Customer customer = customerMapper.fromCustomerRequest(createCustomerRequest);
        customer.setIsDeleted(false);
        customer.setCustomerSegment(customerSegment);
        customer = customerRepository.save(customer);
        KYC kyc = new KYC();
        kyc.setCustomer(customer);
        kyc.setUuid(UUID.randomUUID().toString());
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        kycRepository.save(kyc);

        return customerMapper.toCustomerResponse(customer);
    }

    @Transactional
    @Override
    public void disableCustomerByPhoneNumber(String phoneNumber) {
        if (!customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Phone Number not found");
        }
        customerRepository.disabledByPhoneNumber(phoneNumber);
    }
}
