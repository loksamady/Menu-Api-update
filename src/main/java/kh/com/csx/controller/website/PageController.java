package kh.com.csx.controller.website;

import kh.com.csx.constant.Constant;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.service.admin.PageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API_V1_WEBSITE + "/page")
public class PageController {
    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }
    @GetMapping
    public Response getPages() {
        return new SuccessResponse(this.pageService.getPages());
    }

    @GetMapping("/{id}")
    public Response getPage(@PathVariable("id") Integer id) {
        return new SuccessResponse(this.pageService.getPage(id));
    }
}
