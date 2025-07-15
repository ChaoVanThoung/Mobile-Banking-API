package kh.edu.cstad.mbbanking.dto;

public record CreateCustomerRequest(
        String fullName,
        String gender,
        String email,
        String phoneNumber,
        String remark,
        String nationalCardId,
        String segment
) {
}
