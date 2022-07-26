package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.LlpFx;
import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.type.LlpFxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Package :: com.hanpass.batch.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/09/24
 * Description ::
 */
public interface LlpFxRepository extends JpaRepository<LlpFx, Long> {

    /**
     * find LlpFx by Payment
     * @param payment
     * @return
     */
    Optional<LlpFx> findByPayment(Payment payment);

    /**
     * find by LlpFx by llpFxStatus and fxDate
     * @param llpFxStatus
     * @param fxDate
     * @return
     */
    List<LlpFx> findByLlpFxStatusAndFxDate(LlpFxStatus llpFxStatus, LocalDateTime fxDate);

    /**
     * ind by LlpFx by llpFxStatus and fxDate between
     * @param llpFxStatus
     * @param fxDate
     * @param fxDate2
     * @return
     */
    List<LlpFx> findByLlpFxStatusAndLlpModifyUtcDateBetween(LlpFxStatus llpFxStatus, LocalDateTime fxDate, LocalDateTime fxDate2);

}
