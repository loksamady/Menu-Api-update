package kh.com.csx.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.request.MerchantRequest;
import kh.com.csx.request.MerchantThemeRequest;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.security.RolesOrPermissions;
import kh.com.csx.service.MerchantFileService;
import kh.com.csx.service.admin.MerchantService;
import kh.com.csx.service.admin.MerchantThemeService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static kh.com.csx.constant.RoleConstant.OWNER;

@RestController
@RequestMapping(Constant.API_V1_ADMIN + "/merchants")
public class MerchantController {
    private final MerchantService service;
    private final MerchantFileService merchantFileService;
    private final MerchantThemeService merchantThemeService;

    public MerchantController(
            MerchantService service,
            MerchantFileService merchantFileService,
            MerchantThemeService merchantThemeService
    ) {
        this.service = service;
        this.merchantFileService = merchantFileService;
        this.merchantThemeService = merchantThemeService;
    }

//    @RolesOrPermissions
//    @GetMapping
//    public Response getMerchants(
//            @Valid @NonNegative @RequestParam(name = "page", defaultValue = Constant.DEFAULT_PAGE) Integer page,
//            @Valid @NonNegative @RequestParam(name = "limit", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer limit
//    ) {
//        Pageable pageable = PageRequest.of(Util.getCurrentPage(page), limit);
//        return PageableResponse.build(service.getAll(pageable));
//    }

    @RolesOrPermissions(roles = {OWNER})
    @GetMapping
    public Response getMerchants() {
        return SuccessResponse.build(service.getAll());
    }

    @RolesOrPermissions(roles = {OWNER})
    @GetMapping("/auth")
    public Response getAuthMerchants() {
        return SuccessResponse.build(service.getAuthMerchants());
    }

    @RolesOrPermissions(roles = {OWNER})
    @GetMapping("/options")
    public Response getMerchantOptions() {
        return SuccessResponse.build(service.getOption());
    }

    @RolesOrPermissions(roles = {OWNER})
    @GetMapping("/{id}")
    public Response getMerchant(@PathVariable Integer id, HttpServletRequest request) {
        String baseUrl = String.format("%s://%s:%d%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                Constant.API_V1_WEBSITE);

        return SuccessResponse.build(service.getOne(id, baseUrl));
    }

    @RolesOrPermissions(roles = {OWNER})
    @PostMapping
    public Response createMerchant(@Valid @ModelAttribute MerchantRequest request) throws IOException {
        return SuccessResponse.build(service.create(request));
    }

    @RolesOrPermissions(roles = {OWNER})
    @PutMapping("/{id}")
    public Response updateMerchant(
            @PathVariable Integer id,
            @Valid @RequestBody @ModelAttribute MerchantRequest request
    ) throws IOException {
        return SuccessResponse.build(service.update(id, request));
    }

    @RolesOrPermissions(roles = {OWNER})
    @DeleteMapping("/{id}")
    public Response deleteMerchant(@PathVariable Integer id) {
        return SuccessResponse.build(service.delete(id));
    }

    @RolesOrPermissions(roles = {OWNER})
    @DeleteMapping("/file/{id}")
    public Response deleteMenuFile(@PathVariable("id") Integer id) {
        merchantFileService.deleteFile(id);
        return new SuccessResponse(null);
    }

    @RolesOrPermissions(roles = {OWNER})
    @GetMapping("/theme/{merchantId}")
    public Response getMerchantTheme(@PathVariable Integer merchantId) {
        return SuccessResponse.build(merchantThemeService.getOne(merchantId));
    }

    @RolesOrPermissions(roles = {OWNER})
    @PutMapping("/theme/{merchantId}")
    public Response updateMerchantTheme(
            @PathVariable Integer merchantId,
            @Valid @RequestBody MerchantThemeRequest request
    ) {
        return SuccessResponse.build(merchantThemeService.update(merchantId, request));
    }
}
