package kh.com.csx.controller.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.request.MenuRequest;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.security.RolesOrPermissions;
import kh.com.csx.service.MenuFileService;
import kh.com.csx.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static kh.com.csx.constant.RoleConstant.*;

@RestController
@RequestMapping(Constant.API_V1_ADMIN + "/menus")
public class AdminMenuController {
    @Autowired
    private final MenuService menuService;

    @Autowired
    private final MenuFileService menuFileService;

    public AdminMenuController(MenuService menuService, MenuFileService menuFileService) {
        this.menuService = menuService;
        this.menuFileService = menuFileService;
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @GetMapping
    public Response getMenus(
            @RequestParam(name = "merchantId", defaultValue = "") Integer merchantId,
            @Nullable @RequestParam(name = "status", defaultValue = "") Integer status,
            @Nullable @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy
    ) {
        return new SuccessResponse(menuService.getMenus(merchantId, status, orderBy));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @GetMapping("/{id}")
    public Response getMenu(@PathVariable("id") Integer id) {
        return new SuccessResponse(menuService.getMenu(id));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @PutMapping("/{id}")
    public Response updateMenu(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute MenuRequest menuRequest
    ) throws IOException {
        return new SuccessResponse(menuService.updateMenu(id, menuRequest));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @PostMapping
    public Response createMenu(@Valid @RequestBody @ModelAttribute MenuRequest menuRequest) throws IOException {
        return new SuccessResponse(menuService.createMenu(menuRequest));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @DeleteMapping("/{id}")
    public Response deleteMenu(@PathVariable("id") Integer id) {
        menuService.deleteMenu(id);
        return new SuccessResponse(null);
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN, STAFF})
    @DeleteMapping("/file/{id}")
    public Response deleteMenuFile(@PathVariable("id") Long id) {
        menuFileService.deleteMenuFile(id);
        return new SuccessResponse(null);
    }
}
