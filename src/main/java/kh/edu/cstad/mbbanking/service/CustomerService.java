package kh.edu.cstad.mbbanking.service;

import kh.edu.cstad.mbbanking.domain.Customer;
import kh.edu.cstad.mbbanking.dto.CreateCustomerRequest;
import kh.edu.cstad.mbbanking.dto.CustomerResponse;
import kh.edu.cstad.mbbanking.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {
    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);
    CustomerResponse findByPhoneNumber(String phoneNumber);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);
    void disableCustomerByPhoneNumber(String phoneNumber);


}
