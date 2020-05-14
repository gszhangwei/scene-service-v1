package com.scene.adapters.inbound.rest.output;

import com.scene.adapters.inbound.rest.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiErrOutputDTO {
    private int errCode;
    private String errMsg;

    public static ApiErrOutputDTO fail(int errCode, String errMsg) {
        return new ApiErrOutputDTO(errCode, errMsg);
    }

    public static ApiErrOutputDTO fail(ErrorCode errorCode) {
        return new ApiErrOutputDTO(errorCode.getCode(), errorCode.getMessage());
    }
}
