package kh.edu.cstad.mbbanking.controller;

import kh.edu.cstad.mbbanking.dto.CustomerSegment.CustomerSegmentRequest;
import kh.edu.cstad.mbbanking.dto.CustomerSegment.CustomerSegmentResponse;
import kh.edu.cstad.mbbanking.service.CustomerSegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/CustomerSegment")
@RequiredArgsConstructor
public class CustomerSegmentController {
    private  final CustomerSegmentService customerSegmentService;

    @PostMapping
    CustomerSegmentResponse createNewCustomerSegment(@RequestBody CustomerSegmentRequest customerSegmentRequest) {
        return customerSegmentService.createNewCustomerSegment(customerSegmentRequest);
    }
}
