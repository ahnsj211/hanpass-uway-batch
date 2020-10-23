package com.hanpass.batch.common.error;

import com.hanpass.batch.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

/**
 * Package :: com.hanpass.uway.backend.global.error
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-28
 * Description ::
 */
public enum ServiceError {

    BIND_ERROR("40000", "Bind error"){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .resultCode("40000")
                    .resultMessage(StringUtils.isEmpty(getMessage()) ? "Bind error" : getMessage())
                    .build();
        }
    },
    UNAUTHORIZED("40001", "Unauthorized"){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .resultCode("40001")
                    .resultMessage(StringUtils.isEmpty(getMessage()) ? "Unauthorized" : getMessage())
                    .build();
        }
    },
    FORBIDDEN("40002", "forbidden"){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .resultCode("40002")
                    .resultMessage(StringUtils.isEmpty(getMessage()) ? "forbidden" : getMessage())
                    .build();
        }
    },
    INVALID_PARAM("40003", "Bad request"){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .resultCode("40003")
                    .resultMessage(StringUtils.isEmpty(getMessage()) ? "Bad request" : getMessage())
                    .build();
        }
    },
    NOT_FOUND_API_HEADER("40004", "There is no API Key or GUID Header."){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    INVALID_API_HEADER("40005", "API Key or GUID Header does not match."){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    INTERNAL_SERVER_ERROR("50000", "Internal server error"){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    NOT_FOUND_EXCHANGE_RATE("50001", "Not found exchange rate"){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    NOT_FOUND_ESTIMATE("50002", "Not found estimate of payment."){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    EXPIRED_ESTIMATE("50003", "The Estimate has expired."){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    ALREADY_USED_ESTIMATE("50004", "Estimate aleady used."){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    NOT_FOUND_PAYMENT_INFO("50005", "Not found payment information."){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    ALREADY_REGISTERED_PARTNER_TRX_ID("50006", "Partner transaction ID already registered."){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    FILE_EXTENSION_NOT_ALLOWED("50007", "File extension not allowed."){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    CANNOT_CANCEL_PAYMENT("50008", "cannot cancel payment"){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    },
    CANNOT_REFUND_PAYMENT("50009", "cannot refund payment"){
        @Override
        public ApiResponse<?> getApiResponse() {
            return ApiResponse.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .resultCode(getCode())
                    .resultMessage(getMessage())
                    .build();
        }
    };

    private String code;
    private String message;

    public abstract ApiResponse<?> getApiResponse();

    private ServiceError(){}

    private ServiceError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
