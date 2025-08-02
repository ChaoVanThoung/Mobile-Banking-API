package kh.edu.cstad.mbbanking.controller;

import kh.edu.cstad.mbbanking.dto.MediaResponse;
import kh.edu.cstad.mbbanking.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file){
        return mediaService.upload(file);
    }

    @PostMapping("/multiple")
    public List<MediaResponse> uploadMultiple(@RequestPart MultipartFile[] files){
        return Arrays.stream(files)
                .map(mediaService::upload)
                .toList();
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<Resource> download(@PathVariable String name){
        return mediaService.download(name);
    }

    @GetMapping("/disable/{fileName}")
    public void disableIsDeleteByFileName(@PathVariable String fileName){
        mediaService.disableIsDeleteByFileName(fileName);
    }

    @DeleteMapping("/{filename}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(@PathVariable String filename){
        mediaService.disableIsDeleteByFileName(filename);
    }
}
