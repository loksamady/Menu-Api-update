package kh.com.csx.response;

import lombok.Getter;

@Getter
public class ErrorResponse extends BaseResponse implements Response {

    private final String error;

    public ErrorResponse(int status, String error) {
        super(status);
        this.error = error;
    }

}
