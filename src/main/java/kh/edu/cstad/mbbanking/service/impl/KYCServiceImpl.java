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
    public void verifyByNationalCardId(String nationalCardId) {
        if (!kyCRepository.existsByNationalCardId(nationalCardId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NationalCardId not found");
        }
        kyCRepository.verifyByNationalCardId(nationalCardId);
    }

}
