package com.wink.gongongu.domain.user.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.user.dto.SignUpRequest;
import com.wink.gongongu.domain.user.dto.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "유저관련 API")
public interface UserControllerSpec {

    @Operation(
        summary = "회원가입 API",
        description = """
            카카오 로그인 후 리다이렉트 url의 status 쿼리 파라미터로 "NEW_MEMBER"를 반환받으면 아직 회원가입 절차가 완료되지 않은 유저입니다.<br>
            해당 유저에 한에서 이 API를 호출하여 회원가입을 완료합니다.<br>
            개인형 유저는 role 필드에 "INDIVIDUAL"을, 사업자형 유저는 "BUSINESS"를 넣어주세요.<br>
            businessCode는 사업자형 유저만 입력하는 필드입니다. 개인형 유저는 null로 보내주시면 됩니다.<br>
            region은 행정구역 단위로 개인형 유저인 경우만 필수입니다. 사업자형 유저는 null로 보내주시면 됩니다.<br>
            이미 회원가입을 완료한 유저인 경우 FORBIDDEN 에러가 반환됩니다.
            """
    )
    SignUpResponse singUp(SignUpRequest request, UserPrincipal principal);

}
