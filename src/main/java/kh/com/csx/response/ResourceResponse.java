package kh.com.csx.response;

import kh.com.csx.dto.FileDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResourceResponse extends BaseResponse implements Response {

    ResourceResponse() {
        super(HttpStatus.OK.value());
    }

    ResourceResponse(int status) {
        super(status);
    }

    public static ResponseEntity<Resource> build(FileDTO file) {
        return ResponseEntity.ok()
                .contentType(file.getContentType())
                .header(HttpHeaders.CONTENT_DISPOSITION, file.getHeaderValue())
                .body(file.getResource());
    }
}
