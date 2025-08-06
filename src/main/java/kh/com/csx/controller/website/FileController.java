package kh.com.csx.controller.website;

import kh.com.csx.constant.Constant;
import kh.com.csx.response.ResourceResponse;
import kh.com.csx.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.API_V1_WEBSITE + "/files")
public class FileController {
    private final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @GetMapping("/logo/{fileName:.+}")
    public ResponseEntity<Resource> viewLogo(@PathVariable String fileName) {
        return ResourceResponse.build(service.view(fileName));
    }
}
