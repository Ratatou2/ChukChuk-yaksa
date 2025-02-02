package com.mayak.chuckchuck.service;

import com.mayak.chuckchuck.domain.Diagnosis;
import com.mayak.chuckchuck.domain.PillBag;
import com.mayak.chuckchuck.domain.User;
import com.mayak.chuckchuck.dto.PagingDto;
import com.mayak.chuckchuck.dto.request.DiagnosisInfoResquest;
import com.mayak.chuckchuck.dto.response.DiagnosisResponse;
import com.mayak.chuckchuck.dto.response.DiseaseResponse;
import com.mayak.chuckchuck.dto.response.PillBagResponse;
import com.mayak.chuckchuck.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final PillBagRepository pillBagRepository;
    private final OCRPillsRepository ocrPillsRepository;

    /**
     * 진단서 내역 저장
     * @author: 최서현
     * @param diagnosisInfo
     * @return:
     */
    public void registDianosis(User user, DiagnosisInfoResquest diagnosisInfo) {
        Diagnosis diagnosis = Diagnosis.createDiagnosis(user, diagnosisInfo);
        diagnosisRepository.save(diagnosis);
    }

    /**
     * 진단 기록 조회
     * @author: 김태완
     * @param: page 페이지 번호
     * @return: DiagnosisResponse
     */
    public DiagnosisResponse getDiagnosisList(User user, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "diagnosisDate");

        Page<Diagnosis> diagnosisPage = diagnosisRepository.findAllByUser(user, pageable);
        return DiagnosisResponse.fromEntity(diagnosisPage);
    }

    /**
     * 병력 조회
     * @author: 김태완
     * @param:
     * @return: DiseaseResponse
     */
    public DiseaseResponse getDiseaseResponse(User user) {
        List<Diagnosis> dieaseList = diagnosisRepository.findAllByUser(user);
        return DiseaseResponse.fromEntity(dieaseList);
    }

    /**
     * 병력 조회
     * @author: 김태완
     * @param:
     * @return: PillBagResponse
     */
    public PillBagResponse getPillBagResponse(User user, int page) {
        PagingDto pagingDto = new PagingDto(page, "buildDate");
        Page<PillBag> pillBags = pillBagRepository
                .findByUser(user, pagingDto.getPageable());
        List<PillBagResponse.PillBagDto> pillBagResult = pillBags.getContent().stream()
                .map(pillBag -> {
                    PillBagResponse.PillBagReceipt receipt = PillBagResponse.fromReceipt(pillBag);
                    List<PillBagResponse.OCRPillsGuide> guide = ocrPillsRepository.findByOcrListOcrId(pillBag.getOcrId()).stream()
                            .map(PillBagResponse::fromGuide).toList();

                    return new PillBagResponse.PillBagDto(receipt, guide);
                })
                .collect(Collectors.toList());
        return PillBagResponse.fromEntity((int) pillBags.getTotalElements(), pillBagResult);
    }
}
