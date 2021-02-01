package com.hanpass.batch.common.type;

import com.hanpass.batch.common.error.ServiceError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-02
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum CountryCode {
    KR(Arrays.asList(CurrencyCode.KRW),
            Collections.emptyList()
    ),

    US(Arrays.asList(CurrencyCode.USD),
            Collections.emptyList()
    ),

    CN(Arrays.asList(CurrencyCode.CNY),
            Arrays.asList(
                    RequiredDocType.INVOICE
            )
    ),

    VN(Arrays.asList(CurrencyCode.VND),
            Arrays.asList(
                    RequiredDocType.INVOICE,
                    RequiredDocType.ADMISSION_CONFIRMATION,
                    RequiredDocType.PARENT_PASSPORT_COPY,
                    RequiredDocType.STUDENT_PASSPORT_COPY,
                    RequiredDocType.VISA_STAMP_COPY,
                    RequiredDocType.FAMILY_CERTIFICATE
            )
    ),

    JP(Arrays.asList(CurrencyCode.JPY),
            Collections.emptyList()
    ),

    ID(Arrays.asList(CurrencyCode.IDR),
            Collections.emptyList()
    ),

    MN(Arrays.asList(CurrencyCode.MNT),
            Arrays.asList(
                    RequiredDocType.INVOICE
            )
    );

    // 통화목록
    private final List<CurrencyCode> currencyCodes;

    // 첨부문서
    private final List<RequiredDocType> requiredDocTypes;


}
