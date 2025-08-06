package kh.com.csx.controller.admin;

import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.entity.User;
import kh.com.csx.request.CreateUserRequest;
import kh.com.csx.request.UpdateUserRequest;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.security.RolesOrPermissions;
import kh.com.csx.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static kh.com.csx.constant.RoleConstant.ADMIN;
import static kh.com.csx.constant.RoleConstant.OWNER;

@RestController
@RequestMapping(Constant.API_V1_ADMIN + "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RolesOrPermissions(roles = {OWNER})
    @GetMapping("/owners")
    public Response getOwnerUsers() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new SuccessResponse(this.userService.getOwnerUsers(currentUser.getVendorId()));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN})
    @GetMapping
    public Response getUsers(@RequestParam(required = false) Integer merchantId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer merchantIdFilter = currentUser.getMerchantId() != null ? currentUser.getMerchantId() : merchantId;

        return new SuccessResponse(this.userService.getUsers(merchantIdFilter));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN})
    @GetMapping("/{id}")
    public Response getUser(@PathVariable("id") Long id) {
        return new SuccessResponse(userService.getUser(id));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN})
    @PostMapping
    public Response createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer vendorId = currentUser.getVendorId() != null
                ? currentUser.getVendorId()
                : createUserRequest.getVendorId();
        if (vendorId != null) createUserRequest.setVendorId(vendorId);

        return new SuccessResponse(userService.createUser(createUserRequest));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN})
    @PutMapping("/{id}")
    public Response updateUser(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return new SuccessResponse(this.userService.updateUser(id, updateUserRequest));
    }

    @RolesOrPermissions(roles = {OWNER, ADMIN})
    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUser(id);
        return new SuccessResponse(null);
    }
}
