package com.hanpass.batch.payment.service;

import com.hanpass.batch.common.dto.ApiResponse;
import com.hanpass.batch.common.entity.*;
import com.hanpass.batch.common.error.NoRollbackServiceException;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.repository.PaymentRepository;
import com.hanpass.batch.common.repository.PaymentRepositorySupport;
import com.hanpass.batch.common.type.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Package :: com.hanpass.batch.payment.service
 * Developer :: Ahn Seong-jin
 * Date :: 2021/02/23
 * Description ::
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentBatchService {


}
