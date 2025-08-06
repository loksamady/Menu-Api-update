package kh.com.csx.controller.admin;

import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.dto.SubscriptionTypeDTO;
import kh.com.csx.request.subscriptionType.SubscriptionTypeFilterRequest;
import kh.com.csx.request.subscriptionType.SubscriptionTypeRequest;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.security.RolesOrPermissions;
import kh.com.csx.service.admin.SubscriptionTypeService;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kh.com.csx.constant.RoleConstant.SUPER_ADMIN;

@RestController
@RequestMapping(Constant.API_V1_ADMIN + "/subscription-types")
public class SubscriptionTypeController {
    private final SubscriptionTypeService service;

    public SubscriptionTypeController(SubscriptionTypeService service) {
        this.service = service;
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @GetMapping
    public ResponseEntity<SuccessResponse> getSubscriptionTypes(
            @Valid @NonNegative @RequestParam(name = "page", defaultValue = Constant.DEFAULT_PAGE) Integer page,
            @Valid @NonNegative @RequestParam(name = "limit", defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer limit,
            @ModelAttribute SubscriptionTypeFilterRequest filterRequest
    ) {
        Pageable pageable = PageRequest.of(
                page - 1,
                limit,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<SubscriptionTypeDTO> result = service.getAll(filterRequest, pageable);
        SuccessResponse response = new SuccessResponse(
                Constant.HTTP_STATUS_OK,
                "Subscription Types retrieved successfully",
                result.getTotalElements(),
                result.getContent()
        );

        return ResponseEntity.ok(response);
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @GetMapping("/options")
    public Response getSubscriptionTypeOptions() {
        return SuccessResponse.build(service.getOption());
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @GetMapping("/{id}")
    public Response getSubscriptionType(@PathVariable Integer id) {
        return SuccessResponse.build(service.getOne(id));
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @PostMapping
    public Response createSubscriptionType(@Valid @RequestBody SubscriptionTypeRequest request) {
        return SuccessResponse.build(service.create(request));
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @PutMapping("/{id}")
    public Response updateSubscriptionType(@PathVariable Integer id, @Valid @RequestBody SubscriptionTypeRequest request) {
        return SuccessResponse.build(service.update(id, request));
    }

    @RolesOrPermissions(roles = {SUPER_ADMIN})
    @DeleteMapping("/{id}")
    public Response deleteSubscriptionTypes(@PathVariable Integer id) {
        return SuccessResponse.build(service.delete(id));
    }
}
