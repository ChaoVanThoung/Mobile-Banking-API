package kh.edu.cstad.mbbanking.controller;

import kh.edu.cstad.mbbanking.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbbanking.dto.account.AccountResponse;
import kh.edu.cstad.mbbanking.dto.account.CreateAccountRequest;
import kh.edu.cstad.mbbanking.dto.account.UpdateAccountRequest;
import kh.edu.cstad.mbbanking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/disable/{actNo}")
    void disableAccount (@PathVariable String actNo){
        accountService.disableAccountByActNo(actNo);
    }

    @PatchMapping("/{actNo}")
    AccountResponse updatePartAccountByActNo(@PathVariable String actNo,
                                             @RequestBody UpdateAccountRequest updateAccountRequest){
        return accountService.updatePartAccountByActNo(actNo,updateAccountRequest);
    }

    @DeleteMapping("/{actNo}")
    void deleteByActNo(@PathVariable String actNo){
        accountService.deleteByActNo(actNo);
    }

    @GetMapping("/cust/{custId}")
    List<AccountResponse> findByCustomerId (@PathVariable Integer custId) {
        return accountService.findByCustomerId(custId);
    }

    @GetMapping("/{actNo}")
    AccountResponse findAccountByActNo(@PathVariable String actNo){
        return accountService.findAccountByActNo(actNo);
    }

    @GetMapping
    List<AccountResponse> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    AccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        return accountService.createAccount(createAccountRequest);
    }

}
