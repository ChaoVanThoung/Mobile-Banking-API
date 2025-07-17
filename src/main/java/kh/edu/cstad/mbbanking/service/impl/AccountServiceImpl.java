package kh.edu.cstad.mbbanking.service.impl;

import kh.edu.cstad.mbbanking.domain.Account;
import kh.edu.cstad.mbbanking.domain.AccountType;
import kh.edu.cstad.mbbanking.domain.Customer;
import kh.edu.cstad.mbbanking.dto.account.AccountResponse;
import kh.edu.cstad.mbbanking.dto.account.CreateAccountRequest;
import kh.edu.cstad.mbbanking.dto.account.UpdateAccountRequest;
import kh.edu.cstad.mbbanking.mapper.AccountMapper;
import kh.edu.cstad.mbbanking.repository.AccountRepository;
import kh.edu.cstad.mbbanking.repository.AccountTypeRepository;
import kh.edu.cstad.mbbanking.repository.CustomerRepository;
import kh.edu.cstad.mbbanking.service.AccountService;
import kh.edu.cstad.mbbanking.util.CurrencyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;

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

        Account account = new Account();

        // validation accountType
        AccountType accountType = accountTypeRepository.findByType(createAccountRequest.accountType()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "account type not found")
        );

        // validation Phone Number
        Customer customer = customerRepository.findByPhoneNumber(createAccountRequest.phoneNumber()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer phone number not found")
        );

        switch (createAccountRequest.actCurrency()){
            case CurrencyUtil.USD -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.TEN) < 0){
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,"Balance must be greater than or equal to 10"
                    );
                }

                // set over limit base on customer segment
                if (customer.getCustomerSegment().getSegment().equals("REGULAR")){
                    account.setOverLimit(BigDecimal.valueOf(5000));
                } else if (customer.getCustomerSegment().getSegment().equals("SILVER")){
                    account.setOverLimit(BigDecimal.valueOf(10000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000));
                }
            }
            case CurrencyUtil.KHR -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.valueOf(40000)) < 0){
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,"Balance must be greater than or equal to 10"
                    );
                }

                // set over limit base on customer segment
                if (customer.getCustomerSegment().getSegment().equals("REGULAR")){
                    account.setOverLimit(BigDecimal.valueOf(5000 * 4000));
                } else if (customer.getCustomerSegment().getSegment().equals("SILVER")){
                    account.setOverLimit(BigDecimal.valueOf(10000 * 4000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000 * 4000));
                }
            }
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Currency is not support"
            );
        }

        // validation account no
        if (createAccountRequest.actNo() != null){
            if (accountRepository.existsByActNo(createAccountRequest.actNo())){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"account no already exists");
            }
            account.setActNo(createAccountRequest.actNo());
        } else {
            String actNo;
            do{
                actNo = String.format("%09d",new Random().nextInt(1_000_000_000));
            } while (accountRepository.existsByActNo(actNo));
            account.setActNo(actNo);
        }

        // set data logic
        account.setActName(createAccountRequest.actName());
        account.setActCurrency(createAccountRequest.actCurrency().name());
        account.setBalance(createAccountRequest.balance());
        account.setIsHide(false);
        account.setIsDeleted(false);
        account.setAccountType(accountType);
        account.setCustomer(customer);

        account = accountRepository.save(account);


        return accountMapper.toAccountResponse(account);
    }
}
