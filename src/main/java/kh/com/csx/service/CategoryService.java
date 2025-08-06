package kh.com.csx.service;

import jakarta.transaction.Transactional;
import kh.com.csx.dto.CategoryDTO;
import kh.com.csx.entity.Category;
import kh.com.csx.entity.Merchant;
import kh.com.csx.entity.User;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.CategoryRepository;
import kh.com.csx.repository.MerchantRepository;
import kh.com.csx.request.CategoryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static kh.com.csx.constant.RoleConstant.*;

@Service
public class CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);
    final private RoleService roleService;
    @Autowired
    private CategoryRepository repository;
    private MerchantRepository merchantRepository;

    public CategoryService(
            CategoryRepository repository,
            MerchantRepository merchantRepository,
            RoleService roleService
    ) {
        this.repository = repository;
        this.merchantRepository = merchantRepository;
        this.roleService = roleService;
    }

    @Transactional
    public List<Category> getCategories(Integer merchantId, Integer status, String orderBy) {
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(orderBy)) sort = Sort.by("displayOrder").ascending();
        else if ("desc".equalsIgnoreCase(orderBy)) sort = Sort.by("displayOrder").descending();

        return status != null
                ? repository.findByMerchantIdAndStatus(merchantId, status, sort)
                : repository.findByMerchantId(merchantId, sort);
    }

    @Transactional
    public List<CategoryDTO> getCategoryOptions() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<String> owner = Set.of(String.format("ROLE_%s", OWNER));
        Set<String> adminAndStaff = Set.of(String.format("ROLE_%s", ADMIN), String.format("ROLE_%s", STAFF));

        if (roleService.hasAnyRole(owner)) {
            // For OWNER user filter categories my their multiple merchant_id and status 1
            List<Integer> merchantIdList = merchantRepository
                    .findByVendorIdAndActive(currentUser.getVendorId(), 1)
                    .stream().map((Merchant::getId)).toList();
            List<CategoryDTO> categories = repository
                    .findByMerchantIdInAndStatus(merchantIdList, 1, null)
                    .stream().map(CategoryDTO::toOptionDTO).toList();

            LOGGER.info("Get category options for OWNER: {}, {}", merchantIdList, categories);

            return categories;
        }

        if (roleService.hasAnyRole(adminAndStaff)) {
            // For ADMIN and STAFF user filter categories my their merchant_id and status 1
            List<CategoryDTO> categories = repository
                    .findByMerchantIdAndStatus(currentUser.getMerchantId(), 1, null)
                    .stream().map(CategoryDTO::toOptionDTO).toList();

            LOGGER.info("Get category options: {}, {}", currentUser.getMerchantId(), categories);

            return categories;
        }

        return repository.findByStatus(1, null).stream().map(CategoryDTO::toOptionDTO).toList();
    }

    @Transactional
    public Category getCategory(Integer id) {
        Category category = repository.findById(id).orElseThrow(APIException::notFound);

        LOGGER.info("Successfully get category: {}", category);

        return category;
    }

    @Transactional
    public Category createCategory(CategoryRequest request) {
        LOGGER.info("Start creating category with data: {}", request);

        Category category = new Category();
        Category savedCategory = repository.save(getCategory(category, request)); // <-- This persists and generates ID

        LOGGER.info("Successfully created category: {}", savedCategory);

        return savedCategory;
    }

    @Transactional
    public Category updateCategory(Integer categoryId, CategoryRequest request) {
        LOGGER.info("Start updating category with ID: {} and data: {}", categoryId, request);

        Category category = this.getCategory(categoryId);
        Category updatedCategory = repository.save(getCategory(category, request));

        LOGGER.info("Successfully updated category: {}", updatedCategory);

        return updatedCategory;
    }

    @Transactional
    public Boolean deleteCategory(Integer id) {
//        repository.removeCategoryFromAllMenus(id);
        Category category = this.getCategory(id);
        category.setDeletedAt(new Date());
        
        repository.save(category);

        LOGGER.info("Successfully delete category with id: {}", id);

        return repository.existsById(id);
    }

    private Category getCategory(Category category, CategoryRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer merchantId = currentUser.getMerchantId() != null
                ? currentUser.getMerchantId()
                : request.getMerchantId();

        if (request.getNameEn() != null) category.setNameEn(request.getNameEn());
        if (request.getNameKh() != null) category.setNameKh(request.getNameKh());
        if (request.getNameCn() != null) category.setNameCn(request.getNameCn());
        if (request.getStatus() != null) category.setStatus(request.getStatus());
        if (request.getDisplayOrder() != null) category.setDisplayOrder(request.getDisplayOrder());
        if (merchantId != null) category.setMerchantId(merchantId);

        return category;
    }
}
