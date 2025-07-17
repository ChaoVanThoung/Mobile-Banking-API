package kh.edu.cstad.mbbanking.repository;

import kh.edu.cstad.mbbanking.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

    Optional<AccountType> findByType(String accountType);
}
