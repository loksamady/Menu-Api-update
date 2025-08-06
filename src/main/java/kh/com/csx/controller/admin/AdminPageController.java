package kh.com.csx.controller.admin;
import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.request.PageRequest;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.service.admin.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API_V1_ADMIN + "/page")
public class AdminPageController {
    @Autowired
    private final PageService pageService;

    public AdminPageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping
    public Response getPages() {
        return new SuccessResponse(this.pageService.adminGetPages());
    }

    @GetMapping("/{id}")
    public Response getPage(@PathVariable("id") Integer id) {
        return new SuccessResponse(this.pageService.getPage(id));
    }

    @PutMapping("/{id}")
    public Response updatePage(@PathVariable("id") Integer id, @Valid @RequestBody PageRequest updatePageRequest) {
        return new SuccessResponse(this.pageService.updatePage(id, updatePageRequest));
    }

    @DeleteMapping("/{id}")
    public Response deletePage(@PathVariable("id") Integer id) {
        this.pageService.deletePage(id);
        return new SuccessResponse(null);
    }

    @PostMapping
    public Response createPage(@Valid @RequestBody PageRequest pageRequest) {
        return new SuccessResponse(pageService.createPage(pageRequest));
    }
}
