package com.hanpass.batch.common.handler;

import com.hanpass.batch.common.dto.ApiResponse;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Package :: com.hanpass.uway.backend.config.handler
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-28
 * Description ::
 */
@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> responseServiceException(ServiceException ex) {
        ApiResponse<?> err = ex.getServiceError().getApiResponse();
        err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
                err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");

        log.error(err.getResultMessage(), new ServiceException(ex.getServiceError(), ex.getServiceError().getMessage()));
        log.error(err.getResultMessage(), ex.getCause());
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
        err.setResultMessage(ex.getMessage());
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err, err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiResponse<?> err = ServiceError.BIND_ERROR.getApiResponse();
		err.setResultMessage(ex.getMessage());
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err, err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiResponse<?> err = ServiceError.INVALID_PARAM.getApiResponse();
		err.setResultMessage(ex.getMessage());
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err, err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();

		err.setResultMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err, err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
		ApiResponse<?> err = ServiceError.BIND_ERROR.getApiResponse();
        err.setResultMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
                                                                        HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        ApiResponse<?> err = ServiceError.INTERNAL_SERVER_ERROR.getApiResponse();
		err.setResultMessage(StringUtils.isEmpty(ex.getMessage()) ?
        		err.getResultMessage() : err.getResultMessage() + "(" + ex.getMessage() + ")");
        log.error(err.getResultMessage(), ex);
        return new ResponseEntity<>(err,err.getHttpStatus());
    }
}
