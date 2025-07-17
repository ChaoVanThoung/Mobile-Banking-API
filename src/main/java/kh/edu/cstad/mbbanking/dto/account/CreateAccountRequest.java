package kh.edu.cstad.mbbanking.dto.account;

import kh.edu.cstad.mbbanking.util.CurrencyUtil;

import java.math.BigDecimal;

public record CreateAccountRequest(
        String actNo,
        String actName,
        BigDecimal balance,
        CurrencyUtil actCurrency,
        String accountType,
        String phoneNumber
        ) {
}
