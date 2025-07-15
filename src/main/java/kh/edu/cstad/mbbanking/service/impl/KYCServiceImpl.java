package kh.edu.cstad.mbbanking.service.impl;

import kh.edu.cstad.mbbanking.repository.KYCRepository;
import kh.edu.cstad.mbbanking.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class KYCServiceImpl implements KYCService {

    private final KYCRepository kyCRepository;

    @Transactional
    @Override
    public void verifyByUuid(String uuid) {
        if (!kyCRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "KYC UUID Not Found");
        }
        kyCRepository.verifyByUuid(uuid);
    }
}
