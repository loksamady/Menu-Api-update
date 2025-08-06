package kh.com.csx.service.admin;

import jakarta.annotation.Nullable;
import kh.com.csx.dto.MerchantDTO;
import kh.com.csx.entity.Merchant;
import kh.com.csx.entity.MerchantFile;
import kh.com.csx.entity.User;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.MerchantRepository;
import kh.com.csx.request.MerchantRequest;
import kh.com.csx.request.MerchantThemeRequest;
import kh.com.csx.service.FileService;
import kh.com.csx.service.MerchantFileService;
import kh.com.csx.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MerchantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantService.class);
    private final MerchantRepository repository;
    private final FileService fileService;
    private final MerchantFileService merchantFileService;
    private final MerchantThemeService merchantThemeService;
    @Value("${file.upload-merchant-logo-dir:uploads/merchant_logos}")
    private String uploadDir;

    public MerchantService(
            MerchantRepository repository,
            FileService fileService,
            MerchantFileService merchantFileService,
            MerchantThemeService merchantThemeService
    ) {
        this.repository = repository;
        this.fileService = fileService;
        this.merchantFileService = merchantFileService;
        this.merchantThemeService = merchantThemeService;
    }

//    @Transactional
//    public Page<MerchantDTO> getAll(Pageable pageable) {
//        Page<Merchant> merchants = repository.findAllByOrderByIdDesc(pageable);
//
//        LOGGER.info("Get all merchants: {}", merchants);
//
//        return new PageImpl<>(
//                merchants.getContent().stream().map(MerchantDTO::toListDTO).toList(),
//                pageable,
//                merchants.getTotalElements()
//        );
//    }

    @Transactional
    public List<MerchantDTO> getAll() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MerchantDTO> merchants = repository.findByVendorId(currentUser.getVendorId())
                .stream().map(MerchantDTO::toListDTO).toList();

        LOGGER.info("Get all merchants: {}", merchants);

        return merchants;
    }

    @Transactional
    public List<MerchantDTO> getAuthMerchants() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MerchantDTO> merchants = repository.findByVendorId(currentUser.getVendorId())
                .stream().map(MerchantDTO::toAuthDTO).toList();

        LOGGER.info("Get all merchants belong to auth user: {}", merchants);

        return merchants;
    }

    @Transactional
    public List<MerchantDTO> getOption() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MerchantDTO> merchants = repository.findByVendorIdAndActive(currentUser.getVendorId(), 1)
                .stream().map(MerchantDTO::toOptionDTO).toList();

        LOGGER.info("Get all merchant options: {}", merchants);

        return merchants;
    }

    @Transactional
    public MerchantDTO getOne(Integer id, String baseUrl) {
        MerchantDTO merchant = repository.findById(id)
                .map(entity -> MerchantDTO.toDetailDTO(entity, baseUrl))
                .orElseThrow(APIException::notFound);

        LOGGER.info("Get merchant: {}", merchant);

        return merchant;
    }

    @Transactional
    public Merchant create(MerchantRequest request) throws IOException {
        LOGGER.info("Start creating merchant with data: {}", request);

        checkMerchantUniqueKey(request, null);
        Merchant merchant = new Merchant();

        if (request.getBanners() != null) {
            List<MerchantFile> banners = merchantFileService.saveFiles(request.getBanners());
            merchant.setBanners(banners);
        }

        Merchant savedMerchant = repository.save(getMerchant(merchant, request, null));

        MerchantThemeRequest merchantThemeRequest = MerchantThemeRequest.builder()
                .primary(request.getPrimary())
                .primaryLight(request.getPrimaryLight())
                .primaryDark(request.getPrimaryDark())
                .secondary(request.getSecondary())
                .secondaryLight(request.getSecondaryLight())
                .secondaryDark(request.getSecondaryDark())
                .build();
        merchantThemeService.create(savedMerchant.getId(), merchantThemeRequest);

        LOGGER.info("Successfully create merchant: {}", savedMerchant);

        return savedMerchant;
    }

    @Transactional
    public Merchant update(Integer id, MerchantRequest request) throws IOException {
        LOGGER.info("Start updating merchant with id and page request : {} {}", id, request);

        checkMerchantUniqueKey(request, id);
        Merchant merchant = repository.findById(id).orElseThrow(APIException::notFound);

        if (request.getBanners() != null) {
            List<MerchantFile> banners = merchantFileService.saveFiles(request.getBanners());
            merchant.getBanners().addAll(banners);
        }

        Merchant updatedMerchant = repository.save(getMerchant(merchant, request, id));

        MerchantThemeRequest.MerchantThemeRequestBuilder builder = MerchantThemeRequest.builder();

        if (request.getPrimary() != null) builder.primary(request.getPrimary());
        if (request.getPrimaryLight() != null) builder.primaryLight(request.getPrimaryLight());
        if (request.getPrimaryDark() != null) builder.primaryDark(request.getPrimaryDark());

        if (request.getSecondary() != null) builder.secondary(request.getSecondary());
        if (request.getSecondaryLight() != null) builder.secondaryLight(request.getSecondaryLight());
        if (request.getSecondaryDark() != null) builder.secondaryDark(request.getSecondaryDark());

        merchantThemeService.update(updatedMerchant.getId(), builder.build());

        LOGGER.info("Successfully update merchant: {}", updatedMerchant);

        return updatedMerchant;
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        Merchant merchant = repository.findById(id).orElse(null);

        if (merchant != null) {
            merchant.setActive(status);
            repository.save(merchant);
            LOGGER.info("Successfully update merchant status: id={}, status={}", id, status);
        }
    }

    @Transactional
    public Boolean delete(Integer id) {
        Merchant merchant = repository.findById(id).orElseThrow(APIException::notFound);
        merchant.setDeletedAt(new Date());

        repository.save(merchant);

        LOGGER.info("Successfully delete merchant id: {}", id);

        return !repository.existsById(id);

    }

    private Merchant getMerchant(Merchant merchant, MerchantRequest request, @Nullable Integer id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        merchant.setVendorId(currentUser.getVendorId());
        if (request.getName() != null) merchant.setName(request.getName());
        if (request.getPrimaryPhone() != null) merchant.setPrimaryPhone(request.getPrimaryPhone());
        merchant.setSecondaryPhone(request.getSecondaryPhone());
        merchant.setAddress(request.getAddress());
        merchant.setTitleEn(request.getTitleEn());
        merchant.setTitleKh(request.getTitleKh());
        merchant.setTitleCn(request.getTitleCn());
        merchant.setSubtitleEn(request.getSubtitleEn());
        merchant.setSubtitleKh(request.getSubtitleKh());
        merchant.setSubtitleCn(request.getSubtitleCn());
        merchant.setDescriptionEn(request.getDescriptionEn());
        merchant.setDescriptionKh(request.getDescriptionKh());
        merchant.setDescriptionCn(request.getDescriptionCn());
        merchant.setLocation(request.getLocation());
        merchant.setOpenTime(request.getOpenTime());
        merchant.setCloseTime(request.getCloseTime());
        merchant.setTelegram(request.getTelegram());
        merchant.setFacebook(request.getFacebook());
        merchant.setInstagram(request.getInstagram());
        merchant.setTiktok(request.getTiktok());
        merchant.setActive(request.getActive() ? 1 : 0);
        String slug = request.getSlug() != null && !request.getSlug().isEmpty()
                ? request.getSlug()
                : Util.toSlug(request.getName());
        merchant.setSlug(slug);

        if (id != null && request.getIsLogoDeleted()) {
            // Delete logo
            merchant.setLogo(null);
            LOGGER.info("delete logo");
        } else if (request.getLogo() != null && request.getLogo().getSize() > 0) {
            // Save logo
            String originalFilename = Util.slugify(Objects.requireNonNull(request.getLogo().getOriginalFilename()));
            String uniqueFilename = Util.getUniqueFileName("merchant", slug, originalFilename);

            merchant.setLogo(uniqueFilename);
            fileService.save(request.getLogo(), uniqueFilename);
            LOGGER.info("save logo");
        }

        return merchant;
    }

    private void checkMerchantUniqueKey(MerchantRequest request, @Nullable Integer id) {
        Boolean isMerchantNameExisted = id != null
                ? repository.existsByNameAndIdNot(request.getName(), id)
                : repository.existsByName(request.getName());
        if (isMerchantNameExisted) throw APIException.conflict("Merchant's name is already token.");

        Boolean isMerchantPrimaryPhoneExisted = id != null
                ? repository.existsByPrimaryPhoneAndIdNot(request.getPrimaryPhone(), id)
                : repository.existsByPrimaryPhone(request.getPrimaryPhone());
        if (isMerchantPrimaryPhoneExisted) throw APIException.conflict("Merchant's primary phone is already token.");

        Boolean isMerchantSlugExisted = id != null
                ? repository.existsBySlugAndIdNot(request.getSlug(), id)
                : repository.existsBySlug(request.getSlug());
        if (isMerchantSlugExisted) throw APIException.conflict("Merchant's slug is already token.");
    }
}
