package kh.edu.cstad.mbbanking.dto.account;

import java.math.BigDecimal;

public record AccountResponse(
        String actNo,
        BigDecimal balance,
        BigDecimal overLimit
) {
}
