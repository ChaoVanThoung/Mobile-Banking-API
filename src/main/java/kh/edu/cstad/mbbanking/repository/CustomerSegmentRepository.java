package kh.edu.cstad.mbbanking.repository;

import kh.edu.cstad.mbbanking.domain.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment, Integer> {

    Optional<CustomerSegment> findBySegment(String segment);
}
