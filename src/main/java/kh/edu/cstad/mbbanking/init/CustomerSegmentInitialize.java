package kh.edu.cstad.mbbanking.init;

import jakarta.annotation.PostConstruct;
import kh.edu.cstad.mbbanking.domain.CustomerSegment;
import kh.edu.cstad.mbbanking.repository.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerSegmentInitialize {

    private final CustomerSegmentRepository customerSegmentRepository;

    @PostConstruct
    void inti(){
        if (customerSegmentRepository.count() == 0) {
            CustomerSegment regular = new CustomerSegment();
            regular.setSegment("REGULAR");
            regular.setIsDeleted(false);
            regular.setDescription("Regular Segment");

            CustomerSegment silver = new CustomerSegment();
            silver.setSegment("SILVER");
            silver.setIsDeleted(false);
            silver.setDescription("Silver Segment");

            CustomerSegment gold = new CustomerSegment();
            gold.setSegment("GOLD");
            gold.setIsDeleted(false);
            gold.setDescription("Gold Segment");

            customerSegmentRepository.saveAll(List.of(regular,silver,gold));

        }
    }
}
