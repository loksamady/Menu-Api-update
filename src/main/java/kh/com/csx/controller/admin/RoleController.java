package kh.com.csx.controller.admin;

import kh.com.csx.constant.Constant;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.API_V1_ADMIN + "/roles")
public class RoleController {
    @Autowired
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public Response getRoles() {
        return new SuccessResponse(this.roleService.getRoles());
    }
}
