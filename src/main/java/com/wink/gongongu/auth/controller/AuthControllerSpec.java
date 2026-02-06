package com.wink.gongongu.auth.controller;

import com.wink.gongongu.auth.dto.LoginResponse;
import com.wink.gongongu.auth.dto.SignUpRequest;
import com.wink.gongongu.auth.dto.SignUpResponse;
import com.wink.gongongu.auth.dto.TestTokenIssueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Operation(
        summary = "로그인",
        description = """
            카카오 로그인 시 쿠키에 LOGIN_TOKEN이 저장됩니다.
            서버는 쿠키에 저장된 LOGIN_TOKEN를 받아서 카카오로 로그인한 유저를 조회하고 로그인합니다.
            발급받은 엑세스토큰을 헤더에 담아 인증이 필요한 API를 호출해주세요.
            쿠키가 전송되도록 프론트에서 credentials 옵션을 활성화 해주세요.
            이 API 호출 후 쿠키에 저장된 LOGIN_TOKEN은 삭제됩니다.
            LOGIN_TOKEN은 쿠키로 전송되기에 스웨거에 표시된 LOGIN_TOKEN 입력란은 비워두셔도 됩니다. (의미없음)
            """
    )
    LoginResponse login( String loginToken, HttpServletResponse response);

    @Operation(
        summary = "회원가입",
        description = """
            카카오 로그인 후 회원가입이 완료되지 않는 유저는 회원가입을 진행합니다.
            카카오 로그인 시 쿠키에 저장된 LOGIN_TOKEN과 회원가입 정보를 받아서 회원가입을 진행합니다.
            role에는 꼭 INDIVIDUAL(개인회원) 또는 BUSINESS(사업자회원) 중 하나를 값으로 보내주세요.
            발급받은 엑세스토큰을 헤더에 담아 인증이 필요한 API를 호출해주세요.
            쿠키가 전송되도록 프론트에서 credentials 옵션을 활성화 해주세요.
            이 API 호출 후 이제 쿠키에 저장된 LOGIN_TOKEN은 삭제됩니다.
            LOGIN_TOKEN은 쿠키로 전송되기에 스웨거에 표시된 LOGIN_TOKEN 입력란은 비워두셔도 됩니다. (의미없음)
            """
    )
    SignUpResponse signup(String loginToken, SignUpRequest request,
        HttpServletResponse response);

}
