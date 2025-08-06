package kh.com.csx.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

class BaseResponse {
    private final OffsetDateTime timestamp;
    @Setter
    @Getter
    private int status;

    BaseResponse(int status) {
        this.timestamp = OffsetDateTime.now();
        this.status = status;
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return timestamp.format(formatter);
    }

}
