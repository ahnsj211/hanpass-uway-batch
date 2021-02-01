package com.hanpass.batch.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import com.hanpass.batch.common.vo.TermsAgreeVo;
import org.springframework.util.ObjectUtils;

import javax.persistence.AttributeConverter;

/**
 * Package :: com.hanpass.uway.common.converter
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/02
 * Description ::
 */
public class TermsAgreeConverter implements AttributeConverter<TermsAgreeVo, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TermsAgreeVo attribute) {
        try {
            return ObjectUtils.isEmpty(attribute) ?
                    null : objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                    "Fail to serialize terms agreement information.", e);
        }
    }

    @Override
    public TermsAgreeVo convertToEntityAttribute(String dbData) {
        try {
            return ObjectUtils.isEmpty(dbData) ?
                    null : objectMapper.readValue(dbData, TermsAgreeVo.class);
        } catch (JsonProcessingException e) {
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                    "Fail to deserialize terms agreement information.", e);
        }
    }
}
