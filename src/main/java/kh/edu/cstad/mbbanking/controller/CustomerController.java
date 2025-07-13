package kh.edu.cstad.mbbanking.controller;

import kh.edu.cstad.mbbanking.dto.CreateCustomerRequest;
import kh.edu.cstad.mbbanking.dto.CustomerResponse;
import kh.edu.cstad.mbbanking.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbbanking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("/{phoneNumber}")
    CustomerResponse updateCustomer(@PathVariable String phoneNumber,
                                    @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.updateByPhoneNumber(phoneNumber, updateCustomerRequest);
    }

    @GetMapping("/{phoneNumber}")
    CustomerResponse findByPhoneNumber(@PathVariable String phoneNumber) {
        return customerService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CustomerResponse createNew(@RequestBody CreateCustomerRequest  createCustomerRequest) {
        return customerService.createNew(createCustomerRequest);
    }
}
