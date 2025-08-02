package kh.edu.cstad.mbbanking.repository;

import kh.edu.cstad.mbbanking.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MediaRepository extends JpaRepository<Media, Integer> {

    Optional<Media> findMediaByNameAndExtension(String name, String extension);
    Boolean existsMediaByName(String name);

}
