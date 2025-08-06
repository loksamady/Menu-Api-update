package kh.com.csx.service.admin;

import jakarta.annotation.Nullable;
import kh.com.csx.entity.MerchantTheme;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.MerchantThemeRepository;
import kh.com.csx.request.MerchantThemeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MerchantThemeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantThemeService.class);
    private final MerchantThemeRepository repository;

    public MerchantThemeService(MerchantThemeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MerchantTheme getOne(Integer merchantId) {
        MerchantTheme theme = repository.findByMerchantId(merchantId).orElseThrow(APIException::notFound);

        LOGGER.info("Get theme by merchant id: {}", theme);

        return theme;
    }

    @Transactional
    public MerchantTheme create(Integer merchantId, @Nullable MerchantThemeRequest request) {
        MerchantTheme merchantTheme = new MerchantTheme();
        merchantTheme.setMerchantId(merchantId);
        MerchantTheme savedMerchantTheme = repository.save(request != null
                ? getMerchantTheme(merchantTheme, request)
                : merchantTheme);

        LOGGER.info("Created theme by merchant id: {}", savedMerchantTheme);

        return savedMerchantTheme;
    }

    @Transactional
    public MerchantTheme update(Integer merchantId, MerchantThemeRequest request) {
        MerchantTheme merchantTheme = repository.findByMerchantId(merchantId).orElse(null);

        if (merchantTheme != null) {
            MerchantTheme updatedMerchantTheme = repository.save(getMerchantTheme(merchantTheme, request));

            LOGGER.info("Updated theme by merchant id: {}", updatedMerchantTheme);

            return updatedMerchantTheme;
        } else {
            return create(merchantId, request);
        }
    }

    private MerchantTheme getMerchantTheme(MerchantTheme merchantTheme, MerchantThemeRequest request) {
        if (request.getPrimary() != null) merchantTheme.setPrimary(request.getPrimary());
        if (request.getPrimaryLight() != null) merchantTheme.setPrimaryLight(request.getPrimaryLight());
        if (request.getPrimaryDark() != null) merchantTheme.setPrimaryDark(request.getPrimaryDark());
        if (request.getSecondary() != null) merchantTheme.setSecondary(request.getSecondary());
        if (request.getSecondaryLight() != null) merchantTheme.setSecondaryLight(request.getSecondaryLight());
        if (request.getSecondaryDark() != null) merchantTheme.setSecondaryDark(request.getSecondaryDark());

        return merchantTheme;
    }
}
