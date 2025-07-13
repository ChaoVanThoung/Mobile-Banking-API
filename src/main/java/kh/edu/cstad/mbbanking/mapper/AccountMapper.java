package kh.edu.cstad.mbbanking.mapper;

import kh.edu.cstad.mbbanking.domain.Account;
import kh.edu.cstad.mbbanking.dto.account.AccountResponse;
import kh.edu.cstad.mbbanking.dto.account.CreateAccountRequest;
import kh.edu.cstad.mbbanking.dto.account.UpdateAccountRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    // Update by part
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromAccountPartially(UpdateAccountRequest updateAccountRequest,
                              @MappingTarget Account account);

    // create new account
    Account fromCreateAccountRequest(CreateAccountRequest createAccountRequest);

    // Account Response
    AccountResponse toAccountResponse (Account account);
}
