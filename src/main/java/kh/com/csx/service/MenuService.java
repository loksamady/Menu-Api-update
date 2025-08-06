package kh.com.csx.service;

import jakarta.transaction.Transactional;
import kh.com.csx.entity.Category;
import kh.com.csx.entity.Menu;
import kh.com.csx.entity.MenuFile;
import kh.com.csx.entity.User;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.MenuRepository;
import kh.com.csx.request.MenuRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MenuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MenuFileService menuFileService;

    public MenuService(MenuRepository menuRepository, CategoryService categoryService, MenuFileService menuFileService) {
        this.menuRepository = menuRepository;
        this.categoryService = categoryService;
        this.menuFileService = menuFileService;
    }

    public List<Menu> getMenus(Integer merchantId, Integer status, String orderBy) {
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(orderBy)) sort = Sort.by("menuId").ascending();
        else if ("desc".equalsIgnoreCase(orderBy)) sort = Sort.by("menuId").descending();

        return status != null
                ? menuRepository.findByMerchantIdAndStatus(merchantId, status, sort)
                : menuRepository.findByMerchantId(merchantId, sort);
    }

    public Menu getMenu(Integer id) {
        Menu menu = menuRepository.findById(id).orElseThrow(APIException::notFound);

        LOGGER.info("Successfully get menu: {}", menu);

        return menu;
    }

    @Transactional
    public Menu createMenu(MenuRequest request) throws IOException {
        LOGGER.info("Start creating menu with data: {}", request);

        Menu menu = new Menu();

        if (request.getFiles() != null) {
            List<MenuFile> menuFiles = menuFileService.saveFiles(request.getFiles());
            menu.setFiles(menuFiles);
        }

        Menu savedMenu = menuRepository.save(getMenuData(menu, request)); // <-- This persists and generates ID

        LOGGER.info("Successfully created menu: {}", savedMenu);

        return savedMenu;
    }

    @Transactional
    public Menu updateMenu(Integer menuId, MenuRequest request) throws IOException {
        LOGGER.info("Start updating menu with ID: {} and data: {}", menuId, request);
        // Retrieve the existing category (assuming you have a repository)
        Menu menu = this.getMenu(menuId);

        if (request.getFiles() != null) {
            List<MenuFile> menuFiles = menuFileService.saveFiles(request.getFiles());
            menu.getFiles().addAll(menuFiles);
        }

        if (request.getSetMainFileId() != null) {
            menu.getFiles().forEach(file -> {
                file.setMain(file.getMenuFileId().equals(request.getSetMainFileId()));
            });
        }

        Menu updatedMenu = menuRepository.save(getMenuData(menu, request)); // <-- This persists and generates ID

        LOGGER.info("Successfully updated menu: {}", updatedMenu);

        return updatedMenu;
    }

    @Transactional
    public void deleteMenu(Integer id) {
        Menu menu = this.getMenu(id);
        menu.setDeletedAt(new Date());

        menuRepository.save(menu);

        LOGGER.info("Successfully delete menu with id: {}", id);
    }

    private Menu getMenuData(Menu menu, MenuRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer merchantId = currentUser.getMerchantId() != null
                ? currentUser.getMerchantId()
                : request.getMerchantId();

        if (request.getNameEn() != null) menu.setNameEn(request.getNameEn());
        if (request.getNameKh() != null) menu.setNameKh(request.getNameKh());
        if (request.getNameCn() != null) menu.setNameCn(request.getNameCn());
        menu.setCode(request.getCode());
        menu.setDescriptionEn(request.getDescriptionEn());
        menu.setDescriptionKh(request.getDescriptionKh());
        menu.setDescriptionCn(request.getDescriptionCn());
        menu.setPrice(request.getPrice());
        menu.setPriceKh(request.getPriceKh());
        menu.setDiscount(request.getDiscount());
        menu.setStatus(request.getStatus());
        menu.setHot(request.isHot());
        if (merchantId != null) menu.setMerchantId(merchantId);

        if (request.getCategories() != null) {
            List<Category> categories = new ArrayList<>();

            request.getCategories().forEach(integer -> {
                Category category = categoryService.getCategory(integer);
                categories.add(category);
            });

            menu.setCategories(categories);
        }

        return menu;
    }
}
