package kh.com.csx.controller.website;

import kh.com.csx.constant.Constant;
import kh.com.csx.repository.MerchantRepository;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import kh.com.csx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.API_V1_WEBSITE + "/category")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;
    private final MerchantRepository merchantRepository;

    public CategoryController(CategoryService categoryService, MerchantRepository merchantRepository) {
        this.categoryService = categoryService;
        this.merchantRepository = merchantRepository;
    }

    @GetMapping
    public Response getCategories(
//            @PathVariable("slug") String slug,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) Integer status
    ) {
//        Merchant merchant = slug != null
//                ? merchantRepository.findBySlug(slug).orElse(null)
//                : null;
//        Integer merchantId = merchant != null ? merchant.getId() : null;

        return new SuccessResponse(this.categoryService.getCategories(null, status, sortDir));
    }
}
