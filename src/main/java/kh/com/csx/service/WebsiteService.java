package kh.com.csx.service;

import jakarta.transaction.Transactional;
import kh.com.csx.dto.MerchantDTO;
import kh.com.csx.dto.WebsiteDTO;
import kh.com.csx.entity.Category;
import kh.com.csx.entity.Menu;
import kh.com.csx.entity.Merchant;
import kh.com.csx.entity.MerchantTheme;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsiteService.class);

    private final VendorRepository vendorRepository;
    private final MerchantRepository merchantRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final MerchantThemeRepository merchantThemeRepository;

    public WebsiteService(
            VendorRepository vendorRepository,
            MerchantRepository merchantRepository,
            MenuRepository menuRepository,
            CategoryRepository categoryRepository,
            MerchantThemeRepository merchantThemeRepository
    ) {
        this.vendorRepository = vendorRepository;
        this.merchantRepository = merchantRepository;
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.merchantThemeRepository = merchantThemeRepository;
    }

    @Transactional
    public WebsiteDTO getWebsiteData(String slug, String baseUrl) {
        LOGGER.info("Slug: {}", slug);

        Integer active = 1;
        Merchant merchant = merchantRepository.findBySlugAndActive(slug, active).orElseThrow(APIException::notFound);
        Boolean isVendorActive = vendorRepository.existsByIdAndStatus(merchant.getVendorId(), active);
        Sort sort = Sort.by("displayOrder").ascending();

        if (!isVendorActive) throw APIException.notFound();

        List<Menu> menus = menuRepository.findByMerchantIdAndStatus(merchant.getId(), active, null);
        List<Category> categories = categoryRepository.findByMerchantIdAndStatus(merchant.getId(), active, sort);
        MerchantTheme merchantTheme = merchantThemeRepository.findByMerchantId(merchant.getId()).orElse(null);

        WebsiteDTO websiteDTO = new WebsiteDTO();
        websiteDTO.setMerchant(MerchantDTO.toWebsiteDTO(merchant, baseUrl));
        websiteDTO.setMenus(menus);
        websiteDTO.setCategories(categories);
        websiteDTO.setMerchantTheme(merchantTheme);

        return websiteDTO;
    }
}
