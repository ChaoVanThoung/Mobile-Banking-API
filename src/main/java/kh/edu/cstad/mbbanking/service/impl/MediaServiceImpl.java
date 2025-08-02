package kh.edu.cstad.mbbanking.service.impl;


import kh.edu.cstad.mbbanking.domain.Media;
import kh.edu.cstad.mbbanking.dto.MediaResponse;
import kh.edu.cstad.mbbanking.repository.MediaRepository;
import kh.edu.cstad.mbbanking.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.BasicLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse upload(MultipartFile file) {

        // 1. Save file to server path
        // a.bc.png
        String name = UUID.randomUUID().toString();
        int lastIndex = Objects.requireNonNull(file.getOriginalFilename())
                .lastIndexOf(".");
        String extension = file.getOriginalFilename()
                .substring(lastIndex + 1);

        // Create path object
        Path path = Paths.get(serverPath + String.format("%s.%s",
                name, extension));

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload failed");
        }

        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeTypeFile(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .extension(media.getExtension())
                .mimeTypeFile(media.getMimeTypeFile())
                .uri(baseUri + String.format("%s.%s", name, extension))
                .size(file.getSize())
                .build();
    }

    @Override
    public ResponseEntity<Resource> download(String fileName) {

        String name;
        String extension;

        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            name = fileName;
            extension = "";
        } else {
            name = fileName.substring(0, lastDotIndex);
            extension = fileName.substring(lastDotIndex + 1);
        }


        // Find media in database
        Media media = mediaRepository.findMediaByNameAndExtension(name, extension)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Media not found"
                        )
                );

        // Check if media is deleted
        if (Boolean.TRUE.equals(media.getIsDeleted())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Media has been deleted");
        }

        try {
            // Build file path
            Path filePath = Paths.get(serverPath + String.format("%s.%s",
                    media.getName(), media.getExtension()));

            // Check if file exists on server
            if (!Files.exists(filePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "File not found on server");
            }

            // Create resource
            Resource resource = new UrlResource(filePath.toUri());

            // Verify resource is readable
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "File not readable");
            }

            // Get file size
            long fileSize = Files.size(filePath);

            // Build filename for download
            String filename = String.format("%s.%s", media.getName(), media.getExtension());

            // Determine content type
            String contentType = media.getMimeTypeFile();
            if (contentType == null || contentType.isEmpty()) {
                contentType = "application/octet-stream";
            }

            // Return ResponseEntity with proper headers
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + filename + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                    .body(resource);

        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File download failed: " + ex.getMessage());
        }

    }

    @Override
    public void disableIsDeleteByFileName(String fileName) {
        String name;
        String extension;

        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            name = fileName;
            extension = "";
        } else {
            name = fileName.substring(0, lastDotIndex);
            extension = fileName.substring(lastDotIndex + 1);
        }


        // Find media in database
        Media media = mediaRepository.findMediaByNameAndExtension(name, extension)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "filename not found"
                        )
                );
        media.setIsDeleted(true);
        mediaRepository.save(media);
    }

    @Override
    public void deleteByFileName(String fileName) {
        String name;
        String extension;

        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            name = fileName;
            extension = "";
        } else {
            name = fileName.substring(0, lastDotIndex);
            extension = fileName.substring(lastDotIndex + 1);
        }

        Media media = mediaRepository.findMediaByNameAndExtension(name, extension)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "filename not found"
                        )
                );
        log.info("Delete operation executed for media: id={}, fileName={}", media.getId(), fileName);
        mediaRepository.delete(media);

    }


}
