package kh.edu.cstad.mbbanking.repository;

import kh.edu.cstad.mbbanking.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCRepository extends JpaRepository<KYC,String> {
}
