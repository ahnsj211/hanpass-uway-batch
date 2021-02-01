package com.hanpass.batch.common.converter;

import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

/**
 * Package :: com.hanpass.uway.common.converter
 * Developer :: Ahn Seong-jin
 * Date :: 2020/10/21
 * Description ::
 */
@Component
public class CipherConverter implements AttributeConverter<String, String> {

    private static SecretKeySpec secretKeySpec;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public CipherConverter(@Value("${hanpass.db.encrypt.key}") String dbEncryptKey) {
        try {
            key = dbEncryptKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            key = md.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        } catch (Exception e) {
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                    "Fail to create db encrypt key.", e);
        }
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            //aes256
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return StringUtils.isEmpty(attribute) ?
                    null :
                    Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                    "Fail to encrypt raw data", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return StringUtils.isEmpty(dbData) ?
                    null :
                    new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                    "Fail to decrypt raw data", e);
        }
    }
}
