package kh.com.csx.service.admin;

import jakarta.annotation.Nullable;
import kh.com.csx.dto.VendorDTO;
import kh.com.csx.entity.Merchant;
import kh.com.csx.entity.User;
import kh.com.csx.entity.Vendor;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.MerchantRepository;
import kh.com.csx.repository.UserRepository;
import kh.com.csx.repository.VendorRepository;
import kh.com.csx.request.CreateUserRequest;
import kh.com.csx.request.UpdateUserRequest;
import kh.com.csx.request.VendorRequest;
import kh.com.csx.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class VendorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VendorService.class);
    private final VendorRepository repository;
    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;
    private final UserService userService;
    private final MerchantService merchantService;

    public VendorService(
            VendorRepository repository,
            UserRepository userRepository,
            MerchantRepository merchantRepository,
            UserService userService,
            MerchantService merchantService
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.merchantRepository = merchantRepository;
        this.userService = userService;
        this.merchantService = merchantService;
    }

    @Transactional
    public List<VendorDTO> getAll() {
        List<VendorDTO> vendors = repository.findAll().stream().map(VendorDTO::toListDTO).toList();

        LOGGER.info("Get all vendors: {}", vendors);

        return vendors;
    }

    @Transactional
    public List<VendorDTO> getOption() {
        List<VendorDTO> vendors = repository.findAll().stream().map(VendorDTO::toOptionDTO).toList();

        LOGGER.info("Get all vendors options: {}", vendors);

        return vendors;
    }

    @Transactional
    public VendorDTO getOne(Integer id) {
        VendorDTO vendor = repository.findById(id)
                .map(VendorDTO::toDetailDTO)
                .orElseThrow(APIException::notFound);

        LOGGER.info("Get vendor: {}", vendor);

        return vendor;
    }

    @Transactional
    public Vendor create(VendorRequest request) {
        LOGGER.info("Start creating vendor with data: {}", request);

        Vendor vendor = new Vendor();
        Vendor savedVendor = repository.save(getVendor(vendor, request, null));
        if (savedVendor.getId() != null) createUser(savedVendor.getId(), request);

        LOGGER.info("Successfully create vendor: {}", savedVendor);

        return savedVendor;
    }

    @Transactional
    public Vendor update(Integer id, VendorRequest request) {
        LOGGER.info("Start updating vendor with id and page request : {} {}", id, request);

        Vendor vendor = repository.findById(id).orElseThrow(APIException::notFound);
        Vendor updatedVendor = repository.save(getVendor(vendor, request, id));

        // Update all merchants and users related to vendor
        if (request.getStatus() != null) {
            // Merchants
            List<Merchant> merchantsByVendorId = merchantRepository.findByVendorId(id);
            if (merchantsByVendorId != null) merchantsByVendorId.forEach(merchant -> {
                merchantService.updateStatus(merchant.getId(), request.getStatus() ? 1 : 0);
            });

            // Users
            List<User> usersByVendorId = userRepository.findByVendorId(id);
            if (usersByVendorId != null) usersByVendorId.forEach(user -> {
                userService.updateUserStatus(user.getUserId(), request.getStatus() ? 1 : 0);
            });
        }

        if (updatedVendor.getId() != null) {
            User isExistedUser = userRepository.findByUsername(request.getUsername()).orElse(null);
            if (isExistedUser != null) updateUser(isExistedUser.getUserId(), request);
            else createUser(updatedVendor.getId(), request);
        }

        LOGGER.info("Successfully update vendor: {}", updatedVendor);

        return updatedVendor;
    }

    @Transactional
    public Boolean delete(Integer id) {
        Vendor vendor = repository.findById(id).orElseThrow(APIException::notFound);
        vendor.setDeletedAt(new Date());

        // Delete all users related to vendor
        List<User> usersByVendorId = userRepository.findByVendorId(id);
        if (usersByVendorId != null) usersByVendorId.forEach(user -> userService.deleteUser(user.getUserId()));

        repository.save(vendor);

        LOGGER.info("Successfully delete vendor id: {}", id);

        return !repository.existsById(id);

    }

    private Vendor getVendor(Vendor vendor, VendorRequest request, @Nullable Integer id) {
        if (request.getName() != null) vendor.setName(request.getName());
        if (request.getPeriod() != null) vendor.setPeriod(request.getPeriod());
        if (request.getPrice() != null) vendor.setPrice(request.getPrice());
        if (request.getDiscount() != null) vendor.setDiscount(request.getDiscount());
        if (request.getMerchantLimit() != null) vendor.setMerchantLimit(request.getMerchantLimit());
        if (request.getStatus() != null) vendor.setStatus(request.getStatus() ? 1 : 0);

        return vendor;
    }

    private void createUser(Integer vendorId, VendorRequest request) {
        CreateUserRequest createUserRequest = new CreateUserRequest();

        if (request.getUsername() != null) createUserRequest.setUsername(request.getUsername());
        if (request.getUserPassword() != null) createUserRequest.setPassword(request.getUserPassword());
        if (request.getUserRoles() != null) createUserRequest.setRoles(request.getUserRoles());
        if (request.getIsBaseOwner() != null) createUserRequest.setIsBaseOwner(request.getIsBaseOwner());
        createUserRequest.setVendorId(vendorId);

        LOGGER.info("Create vendor user with data: {}", createUserRequest);

        userService.createUser(createUserRequest);
    }

    private void updateUser(Long userId, VendorRequest request) {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        if (request.getUserPassword() != null) updateUserRequest.setPassword(request.getUserPassword());
        if (request.getUserRoles() != null) updateUserRequest.setRoles(request.getUserRoles());

        LOGGER.info("Update vendor user with data: {}", updateUserRequest);

        userService.updateUser(userId, updateUserRequest);
    }
}
