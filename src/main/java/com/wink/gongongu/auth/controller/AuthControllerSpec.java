package com.wink.gongongu.auth.controller;

import com.wink.gongongu.auth.dto.TestTokenIssueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "인증관련 API")
public interface AuthControllerSpec {

    @Operation(
        summary = "테스트용 토큰 발급",
        description = """
            DB에 미리 저장된 테스트 유저에 대한 엑세스 토큰을 발급합니다.<br>
            해당 테스트 유저는 개인형 유저입니다. (userId: 1, role: INDIVIDUAL, nickname: 테스트유저, region: 서울특별시 강남구)<br>
            발급받은 엑세스토큰을 헤더에 담아 인증이 필요한 API를 호출해주세요.<br>
            """
    )
    TestTokenIssueResponse issueTestJwt();


}
