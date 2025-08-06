package kh.com.csx.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ErrorsResponse extends BaseResponse implements Response {
    private Map<String, String> errors;

    public ErrorsResponse(int status) {
        super(status);
        this.errors = new HashMap<>();
    }

    public void addError(String fieldName, String errorMessage) {
        errors.put(fieldName, errorMessage);
    }
}
