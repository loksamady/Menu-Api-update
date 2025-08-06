package kh.com.csx.controller.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.request.CategoryRequest;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.security.RolesOrPermissions;
import kh.com.csx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static kh.com.csx.constant.RoleConstant.*;

@RestController
@RequestMapping(Constant.API_V1_ADMIN + "/categories")
public class AdminCategoryController {
    @Autowired
    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @GetMapping
    public Response getCategories(
            @RequestParam(name = "merchantId", required = false) Integer merchantId,
            @Nullable @RequestParam(name = "status", required = false) Integer status,
            @Nullable @RequestParam(name = "orderBy", required = false) String orderBy
    ) {
        return new SuccessResponse(categoryService.getCategories(merchantId, status, orderBy));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @GetMapping("/options")
    public Response getCategoryOptions() {
        return new SuccessResponse(categoryService.getCategoryOptions());
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @GetMapping("/{id}")
    public Response getCategory(@PathVariable("id") Integer id) {
        return new SuccessResponse(categoryService.getCategory(id));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @PostMapping
    public Response createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return new SuccessResponse(categoryService.createCategory(categoryRequest));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @PutMapping("/{id}")
    public Response updateCategory(@PathVariable("id") Integer id, @Valid @RequestBody CategoryRequest categoryRequest) {
        return new SuccessResponse(categoryService.updateCategory(id, categoryRequest));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @DeleteMapping("/{id}")
    public Response deleteCategory(@PathVariable("id") Integer id) {
        return new SuccessResponse(categoryService.deleteCategory(id));
    }
}
