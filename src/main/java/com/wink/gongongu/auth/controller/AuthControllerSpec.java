package com.wink.gongongu.auth.controller;

import com.wink.gongongu.auth.dto.LoginResponse;
import com.wink.gongongu.domain.user.dto.SignUpRequest;
import com.wink.gongongu.domain.user.dto.SignUpResponse;
import com.wink.gongongu.auth.dto.TestTokenIssueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "인증관련 API")
public interface AuthControllerSpec {

    @Operation(
        summary = "테스트용 토큰 발급",
        description = """
            테스트 유저에 대한 엑세스 토큰을 발급합니다.
            발급받은 엑세스토큰을 헤더에 담아 인증이 필요한 API를 호출해주세요.
            테스트용으로만 사용해주세요. (나중에 이 API는 지울 예정)
            """
    )
    TestTokenIssueResponse issueTestJwt();


}
