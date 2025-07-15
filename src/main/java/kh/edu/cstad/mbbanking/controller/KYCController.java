package kh.edu.cstad.mbbanking.controller;

import kh.edu.cstad.mbbanking.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/KYC")
@RequiredArgsConstructor
public class KYCController {
    private final KYCService kyCService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{uuid}")
    void verifyByUuid(@PathVariable String uuid){
        kyCService.verifyByUuid(uuid);
    }
}
