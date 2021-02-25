package com.hanpass.batch.common.error;

/**
 * Package :: com.hanpass.uway.common.exception
 * Developer :: Ahn Seong-jin
 * Date :: 2020/08/28
 * Description ::
 */
public class NoRollbackServiceException extends Exception {
    private static final long serialVersionUID = -5555534652292426912L;

    private final ServiceError serviceError;

    public NoRollbackServiceException(ServiceError serviceError) {
        super();
        this.serviceError = serviceError;
    }

    public NoRollbackServiceException(ServiceError serviceError, String message) {
        super(message);
        this.serviceError = serviceError;
    }

    public NoRollbackServiceException(ServiceError serviceError, String message, Throwable cause) {
        super(message, cause);
        this.serviceError = serviceError;
    }

    public NoRollbackServiceException(ServiceError serviceError, Throwable cause) {
        super(cause);
        this.serviceError = serviceError;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }
}
