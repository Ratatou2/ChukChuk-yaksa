package com.mayak.chuckchuck.service;

import com.mayak.chuckchuck.domain.Pill;
import com.mayak.chuckchuck.domain.TakeList;
import com.mayak.chuckchuck.domain.TakePills;
import com.mayak.chuckchuck.domain.User;
import com.mayak.chuckchuck.dto.TakeListEach;
import com.mayak.chuckchuck.dto.request.AddPillsToTakeListRequest;
import com.mayak.chuckchuck.dto.request.TakeListRequest;
import com.mayak.chuckchuck.dto.response.ActiveAlarmListResponse;
import com.mayak.chuckchuck.dto.response.TakeListResponse;
import com.mayak.chuckchuck.exception.ErrorCode.CommonErrorCode;
import com.mayak.chuckchuck.exception.RestApiException;
import com.mayak.chuckchuck.repository.PillRepository;
import com.mayak.chuckchuck.repository.TakeListRepository;
import com.mayak.chuckchuck.repository.TakePillsRepository;
import com.mayak.chuckchuck.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TakeListService {
    private final TakeListRepository takeListRepository;
    private final UserRepository userRepository;
    private final TakePillsRepository takePillsRepository;
    private final PillRepository pillRepository;

    /**
     * 복용리스트의 알람 비활성화
     *
     * @author: 최서현
     * @param: takeListId
     */
    public void updateIsAlarmFalse(Long takeListId) {
        Optional<TakeList> takeListOptional = takeListRepository.findById(takeListId);
        if (takeListOptional.isPresent()) {
            TakeList takeList = takeListRepository.findById(takeListId).get();
            takeList.toggleAlarm();
        } else {
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    /**
     * 사용자의 모든 활성화 알람 리스트 조회
     *
     * @author: 최서현xz
     * @param:
     * @return: ActiveAlarmListResponse
     */
    public ActiveAlarmListResponse getAlarmList() {
        //===임시 User 객체 사용
        User user = userRepository.findById(1L).get();
        //===

        List<TakeList> takeLists = takeListRepository.findByUserAndIsAlarmTrue(user);
        return ActiveAlarmListResponse.fromEntity(takeLists);
    }

    /**
     * 알람등록 및 수정
     *
     * @author: 차현철
     * @param: {Long} takeListId
     * @param: {String} alarmTime
     * @param: {int} cycle
     * @return: ResponseEntity.ok()
     */
    public void updateAlarm(Long takeListId, LocalDateTime alarmTime, int cycle) {
        if (0 >= cycle) throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        TakeList takeList = getTakeListOrException(takeListId);
        takeList.updateAlarm(alarmTime, cycle);
    }

    // 복용리스트 있으면 가져오고, 없으면 에러 반환
    private TakeList getTakeListOrException(Long takeListId) {
        return takeListRepository.findById(takeListId).orElseThrow(() ->
                new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    // 유저 있으면 가져오고, 없으면 에러 반환
    private User getUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    /**
     * 사용자의 복용 리스트 조회
     * @author: 김보경
     * @param:
     * @return: TakeListResponse
     * 필터링 기준 :
     * period=false : 복용리스트
     * isFinish 여부 상관없이 전체 복용리스트 조회. 복용중/과거에먹은약 FE에서 isFinish 따라 조회해야 됨.
     * period=true : 문진표
     * 한달 이내 복용리스트 조회, isFinish=false 라면 기간 상관없이 전체조회.
     */
    public TakeListResponse getTakeList(TakeListRequest takeListRequest) {
        /**period t/f 별 기준일자(baseDate) 분기*/
        LocalDateTime baseDate;
        if (takeListRequest.period()) {
            baseDate = LocalDateTime.now().minusMonths(1);
        } else {
            baseDate = LocalDateTime.of(1910, 1, 1, 0, 0, 0);
        }

        //===임시 User 객체 사용
        User user = userRepository.findById(1L).get();
        //===

        List<TakeListEach> takeListEachList = new ArrayList<>();
        List<TakeList> takeLists = takeListRepository.findTakeListByUserIdAndFinishDateAndIsFinish(user, baseDate);
        for (TakeList takeList : takeLists) {
            List<TakePills> byTakeList = takePillsRepository.findByTakeList(takeList);
            TakeListEach takeListEach = TakeListEach.createTakeListEach(takeList, byTakeList);
            takeListEachList.add(takeListEach);

        }
        return TakeListResponse.fromEntity(takeListEachList);

    }

    /**
     * 복용리스트 약 추가
     * @author: 김보경
     * @param: takeListId
     * @return: ResponseEntity.ok()
     */
    public void addPillsToTakeList(Long takeListId, AddPillsToTakeListRequest addPillsToTakeListRequest) {
        TakeList takeList = takeListRepository.findById(takeListId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        List<Long> currentPillIds = takePillsRepository.findPillIdsByTakeListId(takeListId);

        List<Pill> pills = addPillsToTakeListRequest.pills().stream()
                .filter(pillId -> !currentPillIds.contains(pillId))
                .map(pillId -> pillRepository.findById(pillId)
                        .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)))
                .collect(Collectors.toList());

        for (Pill pill : pills) {
            TakePills takePills = new TakePills(takeList, pill);
            takePillsRepository.save(takePills);
        }
    }
}

