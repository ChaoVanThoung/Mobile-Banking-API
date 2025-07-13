package kh.edu.cstad.mbbanking.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
