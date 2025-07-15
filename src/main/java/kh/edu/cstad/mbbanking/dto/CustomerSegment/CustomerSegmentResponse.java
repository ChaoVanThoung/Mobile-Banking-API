package kh.edu.cstad.mbbanking.dto.CustomerSegment;

public record CustomerSegmentResponse(
         String segment,
         String description,
         Boolean isDeleted
) {
}
