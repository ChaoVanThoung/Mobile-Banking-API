package kh.edu.cstad.mbbanking.repository;

import kh.edu.cstad.mbbanking.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface KYCRepository extends JpaRepository<KYC,String> {

    @Modifying
    @Query(value = """
            UPDATE KYC as k
            SET k.isVerified = TRUE
            WHERE k.uuid=?1
            """)
    void verifyByUuid(String uuid);

    Boolean existsByUuid(String uuid);

    Boolean existsByNationalCardId(String nationalCardId);
}
