package kh.edu.cstad.mbbanking.mapper;

import kh.edu.cstad.mbbanking.domain.Account;
import kh.edu.cstad.mbbanking.dto.account.AccountResponse;
import kh.edu.cstad.mbbanking.dto.account.CreateAccountRequest;
import kh.edu.cstad.mbbanking.dto.account.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    // Update by part
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromAccountPartially(UpdateAccountRequest updateAccountRequest,
                              @MappingTarget Account account);

    // create new account
//    Account fromCreateAccountRequest(CreateAccountRequest createAccountRequest);

    // Account Response
    @Mapping(source = "accountType.type", target = "accountType")
    AccountResponse toAccountResponse (Account account);
}
