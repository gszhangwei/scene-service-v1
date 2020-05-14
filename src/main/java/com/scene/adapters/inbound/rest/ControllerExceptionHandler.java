package com.scene.adapters.inbound.rest;

import com.scene.adapters.inbound.rest.output.ApiErrOutputDTO;
import com.scene.domain.file.InvalidFileTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiErrOutputDTO handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ApiErrOutputDTO.fail(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResponseStatusException.class})
    @ResponseBody
    public ResponseEntity<ApiErrOutputDTO> handleResponseStatusException(ResponseStatusException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatus()).body(ApiErrOutputDTO.fail(ErrorCode.GENERAL_ERROR));
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrOutputDTO handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.warn("max upload size exceeded exception", ex);
        return ApiErrOutputDTO.fail(ErrorCode.MAX_UPLOAD_FILE_SIZE_EXCEEDED);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrOutputDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return ApiErrOutputDTO.fail(ErrorCode.REQUEST_PARAMETER_VALID_FAIL.getCode(), fieldError.getDefaultMessage());
    }

    @ExceptionHandler({InvalidFileTypeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrOutputDTO handleInvalidFileTypeException(InvalidFileTypeException ex) {
        String exMessage = ex.getMessage();
        log.error(exMessage, ex);
        ErrorCode errorCode = ErrorCode.errorCode(ex);
        return ApiErrOutputDTO.fail(errorCode.getCode(), Objects.nonNull(exMessage) ? exMessage : errorCode.getMessage());
    }
}
