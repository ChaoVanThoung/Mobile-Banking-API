package kh.edu.cstad.mbbanking.service;

import kh.edu.cstad.mbbanking.dto.CustomerSegment.CustomerSegmentRequest;
import kh.edu.cstad.mbbanking.dto.CustomerSegment.CustomerSegmentResponse;

public interface CustomerSegmentService {

    CustomerSegmentResponse createNewCustomerSegment(CustomerSegmentRequest customerSegmentRequest);

}
