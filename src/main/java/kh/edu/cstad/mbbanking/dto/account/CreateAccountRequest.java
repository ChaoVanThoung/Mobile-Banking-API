package kh.edu.cstad.mbbanking.dto.account;

import java.math.BigDecimal;

public record CreateAccountRequest(
        String actNo,
        BigDecimal balance,
        BigDecimal overLimit,
        Integer custId
        ) {
}
