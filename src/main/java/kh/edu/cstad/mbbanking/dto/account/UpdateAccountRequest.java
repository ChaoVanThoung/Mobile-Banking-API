package kh.edu.cstad.mbbanking.dto.account;

import java.math.BigDecimal;

public record UpdateAccountRequest(
        BigDecimal balance,
        BigDecimal overLimit
){
}
