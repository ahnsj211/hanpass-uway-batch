package com.hanpass.batch.common.error;

/**
 * Package :: com.hanpass.uway.backend.global.error
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-28
 * Description ::
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -5555534652292426912L;

    private final ServiceError serviceError;

    public ServiceException(ServiceError serviceError) {
        super();
        this.serviceError = serviceError;
    }

    public ServiceException(ServiceError serviceError, String message) {
        super(message);
        this.serviceError = serviceError;
    }

    public ServiceException(ServiceError serviceError, String message, Throwable cause) {
        super(message, cause);
        this.serviceError = serviceError;
    }

    public ServiceException(ServiceError serviceError, Throwable cause) {
        super(cause);
        this.serviceError = serviceError;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }
}
