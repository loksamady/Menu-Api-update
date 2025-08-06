package kh.com.csx.controller.admin;

import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.request.VendorRequest;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.security.RolesOrPermissions;
import kh.com.csx.service.admin.VendorService;
import org.springframework.web.bind.annotation.*;

import static kh.com.csx.constant.RoleConstant.SUPER_ADMIN;

@RestController
@RequestMapping(Constant.API_V1_ADMIN + "/vendors")
public class VendorController {
    private final VendorService service;

    public VendorController(VendorService service) {
        this.service = service;
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @GetMapping
    public Response getVendors() {
        return SuccessResponse.build(service.getAll());
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @GetMapping("/options")
    public Response getVendorOptions() {
        return SuccessResponse.build(service.getOption());
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @GetMapping("/{id}")
    public Response getVendor(@PathVariable Integer id) {
        return SuccessResponse.build(service.getOne(id));
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @PostMapping
    public Response createVendor(@Valid @RequestBody VendorRequest request) {
        return SuccessResponse.build(service.create(request));
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @PutMapping("/{id}")
    public Response updateVendor(@PathVariable Integer id, @Valid @RequestBody VendorRequest request) {
        return SuccessResponse.build(service.update(id, request));
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @DeleteMapping("/{id}")
    public Response deleteVendor(@PathVariable Integer id) {
        return SuccessResponse.build(service.delete(id));
    }
}
