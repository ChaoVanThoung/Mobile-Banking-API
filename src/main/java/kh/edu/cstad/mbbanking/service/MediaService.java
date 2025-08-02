package kh.edu.cstad.mbbanking.service;


import kh.edu.cstad.mbbanking.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    MediaResponse upload(MultipartFile file);

    ResponseEntity<Resource> download(String fileName);

    void disableIsDeleteByFileName(String fileName);

    void deleteByFileName(String fileName);
}
