package com.hanpass.batch.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Package :: com.hanpass.uway.backend.global.data.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-28
 * Description ::
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class ApiResponse<T> {

    @Builder.Default
    @JsonProperty(required = false)
    @JsonIgnore
    private HttpStatus httpStatus = HttpStatus.OK;

    @Builder.Default
    @JsonProperty(required = true)
    private String resultCode = "20000";

    @Builder.Default
    @JsonProperty(required = true)
    private String resultMessage = "Success";

    @JsonInclude(value= NON_NULL)
    private T resultData;
}
