package kh.edu.cstad.mbbanking.service.impl;

import kh.edu.cstad.mbbanking.domain.CustomerSegment;
import kh.edu.cstad.mbbanking.dto.CustomerSegment.CustomerSegmentRequest;
import kh.edu.cstad.mbbanking.dto.CustomerSegment.CustomerSegmentResponse;
import kh.edu.cstad.mbbanking.mapper.CustomerSegmentMapper;
import kh.edu.cstad.mbbanking.repository.CustomerSegmentRepository;
import kh.edu.cstad.mbbanking.service.CustomerSegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerSegmentServiceImpl implements CustomerSegmentService {

    private final CustomerSegmentMapper customerSegmentMapper;
    private final CustomerSegmentRepository customerSegmentRepository;

    @Override
    public CustomerSegmentResponse createNewCustomerSegment(CustomerSegmentRequest customerSegmentRequest) {

        CustomerSegment customerSegment = customerSegmentMapper.fromCustomerSegmentRequest(customerSegmentRequest);
        customerSegment.setIsDeleted(false);
        customerSegment = customerSegmentRepository.save(customerSegment);
        return customerSegmentMapper.toCustomerSegmentResponse(customerSegment);
    }
}
