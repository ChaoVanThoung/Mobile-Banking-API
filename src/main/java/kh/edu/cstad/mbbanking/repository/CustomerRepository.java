package kh.edu.cstad.mbbanking.repository;

import kh.edu.cstad.mbbanking.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = """
            UPDATE Customer as c 
            set c.isDeleted=TRUE 
            WHERE c.phoneNumber = ?1
            """)
    void disabledByPhoneNumber(String phoneNumber);

    Optional<Customer> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);
}
