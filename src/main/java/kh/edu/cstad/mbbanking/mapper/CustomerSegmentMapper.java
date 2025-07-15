package kh.edu.cstad.mbbanking.mapper;

import kh.edu.cstad.mbbanking.domain.CustomerSegment;
import kh.edu.cstad.mbbanking.dto.CustomerResponse;
import kh.edu.cstad.mbbanking.dto.CustomerSegment.CustomerSegmentRequest;
import kh.edu.cstad.mbbanking.dto.CustomerSegment.CustomerSegmentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerSegmentMapper {
    CustomerSegment fromCustomerSegmentRequest(CustomerSegmentRequest customerSegmentRequest);
    CustomerSegmentResponse toCustomerSegmentResponse(CustomerSegment customerSegment);
}
