package com.scene.adapters.inbound.rest;

import com.scene.domain.core.BizException;
import com.scene.domain.file.InvalidFileTypeException;
import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_FILE_TYPE(1001, "The type of upload file is not permitted"),
    MAX_UPLOAD_FILE_SIZE_EXCEEDED(1002, "The maximum permitted size is 20MB for each file"),
    REQUEST_PARAMETER_VALID_FAIL(1003, "Request parameter valid failed"),
    INTERNAL_SERVER_ERROR(1004, "Internal server error"),
    GENERAL_ERROR(1005, "");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode errorCode(BizException bizException) {
        Class<? extends BizException> exceptionClass = bizException.getClass();
        return exceptionClass.isInstance(InvalidFileTypeException.class) ? ErrorCode.INVALID_FILE_TYPE : ErrorCode.GENERAL_ERROR;
    }
}
