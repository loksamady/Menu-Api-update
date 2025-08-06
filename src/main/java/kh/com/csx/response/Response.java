package kh.com.csx.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public interface Response {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    default String getMessage() {
        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    default void setMessage(String message) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    default Long getTotalRecords() {
        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    default void setTotalRecords(Long totalRecords) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    default Object getData() {
        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    default String getError() {
        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    default Map<String, String> getErrors() {
        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    default void setErrors(Map<String, String> errors) {
    }
}
