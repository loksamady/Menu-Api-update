package kh.com.csx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@AllArgsConstructor
@Getter
public class FileDTO {
    private String fileName;
    private Resource resource;
    private MediaType contentType;
    private String headerValue;
}