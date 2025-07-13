package kh.edu.cstad.mbbanking.service;

import kh.edu.cstad.mbbanking.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbbanking.dto.account.AccountResponse;
import kh.edu.cstad.mbbanking.dto.account.CreateAccountRequest;
import kh.edu.cstad.mbbanking.dto.account.UpdateAccountRequest;

import java.util.List;

public interface AccountService {

    /**
     * Disable Account By actNo
     * @param actNo from PathVariable
     * @since 13-07-2025 (v1)
     * @author Thoung168
     */
    void disableAccountByActNo(String actNo);

    /**
     * Update an account information by actNo
     * @param actNo from PathVariable
     * @param updateAccountRequest from data update
     * @return accountResponse
     * @since 12-07-2025 (v1)
     * @author Thoung168
     */
    AccountResponse updatePartAccountByActNo(String actNo, UpdateAccountRequest updateAccountRequest);

    /**
     * Delete an account by actNo
     * @param actNo from PathVariable
     * @since 12-072025 (v1)
     * @author Thoung168
     */
    void deleteByActNo(String actNo);

    /**
     * Find accounts by customer Id
     * @param custId from PathVariable
     * @return List of accountResponse
     * @since 12-07-2025
     * @author Thoung168
     */
    List<AccountResponse> findByCustomerId(Integer custId);

    /**
     * Find an account by actNo
     * @param actNo from PathVariable
     * @return accountResponse
     * @since 12-07-2025 (v1)
     * @author Thoung168
     */
    AccountResponse findAccountByActNo(String actNo);

    /**
     * Find all accounts
     * @return List of accountResponse
     * @since 12-07-2025 (v1)
     * @author Thoung168
     */
    List<AccountResponse> getAllAccounts();

    /**
     * Create new account
     * @param createAccountRequest data from create
     * @return accountResponse
     * @since 12-07-2025 (v1)
     * @author Thoung168
     */
    AccountResponse createAccount(CreateAccountRequest createAccountRequest);
}
