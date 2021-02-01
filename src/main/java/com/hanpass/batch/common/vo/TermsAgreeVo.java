package com.hanpass.batch.common.vo;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Package :: com.hanpass.uway.payment.vo
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-28
 * Description :: 약관동의 정보
 */
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TermsAgreeVo {

    // 서비스 이용약관 동의
    @NotNull
    private Boolean serviceTermsAgree;

    // 개인정보 처리방침 동의
    @NotNull
    private Boolean privacyTermsAgree;

    // 결제 약관 동의
    @NotNull
    private Boolean paymentAgree;
}
