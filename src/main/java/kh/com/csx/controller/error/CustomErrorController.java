package kh.com.csx.controller.error;

import kh.com.csx.exception.APIException;
import kh.com.csx.response.Response;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
@AllArgsConstructor
public class CustomErrorController implements ErrorController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);
    private final static String ERROR_PATH = "/error";
    private final ErrorAttributes errorAttributes;

    @RequestMapping(value = ERROR_PATH)
    public Response handleError(WebRequest request) {
        HttpStatus status = getStatus(request);
        APIException apiException = new APIException(status.getReasonPhrase(), status.value());
        LOGGER.error(apiException.getMessage(), apiException);
        throw apiException;

    }

    private HttpStatus getStatus(WebRequest webRequest) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        Integer statusCode = (Integer) errorAttributes.get("status");
        if (statusCode != null) {
            return HttpStatus.valueOf(statusCode);
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;

    }
}
