package kh.edu.cstad.mbbanking.mapper;

import kh.edu.cstad.mbbanking.domain.Customer;
import kh.edu.cstad.mbbanking.domain.CustomerSegment;
import kh.edu.cstad.mbbanking.domain.KYC;
import kh.edu.cstad.mbbanking.dto.CreateCustomerRequest;
import kh.edu.cstad.mbbanking.dto.CustomerResponse;
import kh.edu.cstad.mbbanking.dto.UpdateCustomerRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(
            UpdateCustomerRequest updateCustomerRequest,
            @MappingTarget  Customer customer
    );

    // DTO -> Model
    // Model -> DTO
    // What is source data? (parameter)
    // What is target Data (return_type)
    @Mapping(source = "customerSegment.segment", target = "segment")
    @Mapping(source = "customerSegment.description", target = "segmentDescription")
    CustomerResponse toCustomerResponse(Customer customer);

    Customer fromCustomerRequest(CreateCustomerRequest createCustomerRequest);


}
