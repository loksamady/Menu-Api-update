package kh.com.csx.controller.website;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.csx.constant.Constant;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.service.WebsiteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.API_V1_WEBSITE)
public class WebsiteController {
    private final WebsiteService service;

    public WebsiteController(WebsiteService service) {
        this.service = service;
    }

    @GetMapping("/{slug}")
    public Response getWebsiteData(@PathVariable("slug") String slug, HttpServletRequest httpRequest) {
        String baseUrl = String.format("%s://%s:%d%s",
                httpRequest.getScheme(),
                httpRequest.getServerName(),
                httpRequest.getServerPort(),
                Constant.API_V1_WEBSITE);

        return new SuccessResponse(service.getWebsiteData(slug, baseUrl));
    }
}
