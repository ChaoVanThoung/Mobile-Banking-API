package kh.edu.cstad.mbbanking.repository;

import kh.edu.cstad.mbbanking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Boolean existsByActNo(String actNo);
    Optional<Account> findByActNo(String actNo);
    List<Account> findByCustomer_Id(Integer custId);
}
