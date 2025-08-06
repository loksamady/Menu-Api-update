package kh.com.csx.controller.website;

import kh.com.csx.constant.Constant;
import kh.com.csx.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.API_V1_WEBSITE + "/menus")
public class MenuController {
    @Autowired
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

//    @GetMapping
//    public Response getMenus(@RequestParam(required = false) Integer status) {
//        return new SuccessResponse(this.menuService.getMenus(1, status));
//    }
}
