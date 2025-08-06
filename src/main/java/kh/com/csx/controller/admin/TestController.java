package kh.com.csx.controller.admin;

import jakarta.annotation.Nullable;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class TestController {

    @Autowired
    private final MenuService menuService;

    public TestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/test123")
    public Response getMenus(
            @RequestParam(name = "merchantId", defaultValue = "") Integer merchantId,
            @Nullable @RequestParam(name = "status", defaultValue = "") Integer status,
            @Nullable @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy
    ) {
        return new SuccessResponse(menuService.getMenus(merchantId, status, orderBy));
    }
}
