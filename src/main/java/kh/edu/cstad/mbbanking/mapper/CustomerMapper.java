package kh.edu.cstad.mbbanking.mapper;

import kh.edu.cstad.mbbanking.domain.Customer;
import kh.edu.cstad.mbbanking.dto.CreateCustomerRequest;
import kh.edu.cstad.mbbanking.dto.CustomerResponse;
import kh.edu.cstad.mbbanking.dto.UpdateCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

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

    CustomerResponse toCustomerResponse(Customer customer);
    Customer fromCustomerRequest(CreateCustomerRequest createCustomerRequest);

}
