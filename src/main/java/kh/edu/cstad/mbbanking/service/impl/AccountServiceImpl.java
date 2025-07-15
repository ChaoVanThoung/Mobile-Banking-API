package kh.edu.cstad.mbbanking.service.impl;

import kh.edu.cstad.mbbanking.domain.Account;
import kh.edu.cstad.mbbanking.domain.Customer;
import kh.edu.cstad.mbbanking.dto.account.AccountResponse;
import kh.edu.cstad.mbbanking.dto.account.CreateAccountRequest;
import kh.edu.cstad.mbbanking.dto.account.UpdateAccountRequest;
import kh.edu.cstad.mbbanking.mapper.AccountMapper;
import kh.edu.cstad.mbbanking.repository.AccountRepository;
import kh.edu.cstad.mbbanking.repository.CustomerRepository;
import kh.edu.cstad.mbbanking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;

    @Override
    public void disableAccountByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "account not found")
        );
        account.setIsDeleted(true);
        accountRepository.save(account);
    }

    @Override
    public AccountResponse updatePartAccountByActNo(String actNo, UpdateAccountRequest updateAccountRequest) {

        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
        );

        accountMapper.fromAccountPartially(updateAccountRequest,account);
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public void deleteByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "AccountNo not found."
                )
        );
        accountRepository.delete(account);
    }

    @Override
    public List<AccountResponse> findByCustomerId(Integer custId) {
        List<Account> accountList = accountRepository.findByCustomer_Id(custId);
        return accountList.stream().map(accountMapper::toAccountResponse).toList();
    }

    @Override
    public AccountResponse findAccountByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "AccountNo is not found."
                        )
                );
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        // Validation actNo
        if (accountRepository.existsByActNo(createAccountRequest.actNo())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ActNo already exists");
        }
        // Validation customer id
        Customer customer = customerRepository.findById(createAccountRequest.custId())
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
                );
        Account account = accountMapper.fromCreateAccountRequest(createAccountRequest);
        account.setIsDeleted(false);
        account.setCustomer(customer);

        String segment = customer.getCustomerSegment().getSegment();

        switch (segment.toLowerCase()) {
            case "gold" -> account.setOverLimit(BigDecimal.valueOf(50000));
            case "silver" -> account.setOverLimit(BigDecimal.valueOf(10000));
            case "regular" -> account.setOverLimit(BigDecimal.valueOf(5000));
        }

        if (createAccountRequest.balance().compareTo(account.getOverLimit()) > 0 ){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,"Initial balance exceeds overLimit for your segment"
                );
        }

        account = accountRepository.save(account);


        return accountMapper.toAccountResponse(account);
    }
}
