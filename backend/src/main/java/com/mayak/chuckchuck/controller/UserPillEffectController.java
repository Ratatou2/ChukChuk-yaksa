package com.mayak.chuckchuck.controller;

import com.mayak.chuckchuck.domain.UserPillEffect;
import com.mayak.chuckchuck.dto.request.UserPillEffectMemoRequest;
import com.mayak.chuckchuck.dto.request.UserPillEffectRegistInfoRequest;
import com.mayak.chuckchuck.dto.response.UserPillEffectResponse;
import com.mayak.chuckchuck.service.UserPillEffectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/effects")
@RequiredArgsConstructor
public class UserPillEffectController {
    private final UserPillEffectService userPillEffectService;

    /**
     * 약효기록 리스트 조회 및 검색
     * @author:
     * @param:
     * @return:
     */

    /**
     * 약효기록 상세조회
     * @author 최진학
     * @param pillId
     * @return UserPillEffectResponse
     */
    @GetMapping("/pill/{pillId}")
    public ResponseEntity<UserPillEffectResponse> registUserPillEffect(@PathVariable Long pillId) {
        UserPillEffectResponse userPillEffectResponse = userPillEffectService.registUserPillEffect(pillId);

        return ResponseEntity.ok(userPillEffectResponse);
    }

    /**
     * 약효기록 추가 (있으면 가져오기, 없으면 추가)
     * @author 최진학
     * @param:
     * @return:
     */
    @PostMapping("/")
    public ResponseEntity<Void> registUserPillEffect(@RequestBody UserPillEffectRegistInfoRequest userPillEffectRegistInfoRequest) {
        System.out.println(userPillEffectRegistInfoRequest);
        userPillEffectService.updateUserPillEffect(userPillEffectRegistInfoRequest);


        return ResponseEntity.ok().build();
    }

    /**
     * 약효기록 삭제
     * @author:
     * @param:
     * @return:
     */

    /**
     * 약효기록 삭제
     * @author 최진학
     * @param userPillEffectId (약효 효과 ID)
     * @return HttpStatus.OK
     */
    @DeleteMapping("/pill/{userPillEffectId}")
    public ResponseEntity<Void> updateUserPillEffectIsDelete(@PathVariable Long userPillEffectId){
        userPillEffectService.updateUserPillEffectIsDelete(userPillEffectId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 약효기록 새로운 태그 등록
     * @author:
     * @param:
     * @return:
     */

    /**
     * 약효기록 약효에 태그 추가
     * @author:
     * @param:
     * @return:
     */

    /**
     * 약효기록 - 메모수정
     * @author 최진학
     * @param userPillEffectMemoRequest (메모 수정 request)
     * @return 200 OK
     */
    @PutMapping("/pill/memo")
    public ResponseEntity<Void> updateUserPillEffectIsDelete(@Valid @RequestBody UserPillEffectMemoRequest userPillEffectMemoRequest){
        userPillEffectService.updateUserPillEffectMemo(userPillEffectMemoRequest);

        return ResponseEntity.ok().build();
    }
}
