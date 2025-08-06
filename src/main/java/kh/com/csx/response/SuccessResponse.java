package kh.com.csx.response;

import kh.com.csx.constant.Constant;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.StreamSupport;

@Getter
public class SuccessResponse extends BaseResponse implements Response {
    @Setter
    private String message = null;
    @Setter
    private Long totalRecords = null;
    private Object data = null;

    public SuccessResponse() {
        super(Constant.HTTP_STATUS_OK);
    }

    public SuccessResponse(Object data) {
        super(Constant.HTTP_STATUS_OK);
        this.data = data;
        updateTotalRecords(data);
    }

    public SuccessResponse(int status, Object data) {
        super(status);
        this.data = data;
        updateTotalRecords(data);
    }

    public SuccessResponse(int status, String message, Object data) {
        super(status);
        this.data = data;
        this.message = message;
        updateTotalRecords(data);
    }

    public SuccessResponse(int status, String message, long totalRecords, Object data) {
        super(status);
        this.data = data;
        this.message = message;
        this.totalRecords = totalRecords;
    }

    public static SuccessResponse build(Object data) {
        return new SuccessResponse(data);
    }

    private void updateTotalRecords(Object data) {
        if (data instanceof Iterable<?>) {
            this.totalRecords = StreamSupport.stream(((Iterable<?>) data).spliterator(), false).count();
        } else if (data != null) {
            this.totalRecords = 1L;
        }
    }
}
