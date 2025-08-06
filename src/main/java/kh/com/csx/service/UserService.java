package kh.com.csx.service;

import jakarta.transaction.Transactional;
import kh.com.csx.constant.RoleConstant;
import kh.com.csx.dto.UserDTO;
import kh.com.csx.entity.Role;
import kh.com.csx.entity.User;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.MerchantRepository;
import kh.com.csx.repository.UserRepository;
import kh.com.csx.request.CreateUserRequest;
import kh.com.csx.request.UpdateUserRequest;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final MerchantRepository merchantRepository;
    private final RoleService roleService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            MerchantRepository merchantRepository,
            RoleService roleService
    ) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.merchantRepository = merchantRepository;
        this.roleService = roleService;
    }

    @Transactional
    public List<UserDTO> getOwnerUsers(Integer vendorId) {
        List<UserDTO> users = repository.findByVendorId(vendorId).stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName().equals(RoleConstant.OWNER)))
                .map(UserDTO::toListDTO)
                .toList();

        LOGGER.info("Get users by vendor id: {}, users: {} ", vendorId, users);

        return users;
    }

    @Transactional
    public List<UserDTO> getUsers(Integer merchantId) {
        List<UserDTO> users = repository.findByMerchantId(merchantId).stream().map(UserDTO::toListDTO).toList();

        LOGGER.info("Get user by merchant id: {}, users: {} ", merchantId, users);

        return users;
    }

    @Transactional
    public UserDTO getUser(Long id) {
        User user = repository.findById(id).orElseThrow(APIException::notFound);

        LOGGER.info("Successfully get user: {}", user);

        return UserDTO.toListDTO(user);
    }

    @Transactional
    public Response createUser(CreateUserRequest request) {
        User user = new User();

        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMerchantId(request.getMerchantId());
        if (request.getVendorId() != null) user.setVendorId(request.getVendorId());
        if (request.getIsBaseOwner() != null) user.setIsBaseOwner(request.getIsBaseOwner() ? 1 : 0);
        if (request.getRoles() != null) {
            Set<Role> roles = request.getRoles().stream().map(roleService::getRole).collect(Collectors.toSet());
            user.setRoles(roles);
        }
        user.setReferralId(generateReferralId());

        User savedUser = repository.save(user);

        LOGGER.info("Successfully created user: {}", user.getUsername());

        return new SuccessResponse(savedUser);
    }

    @Transactional
    public Response updateUser(Long id, UpdateUserRequest request) {
        User user = repository.findById(id).orElseThrow(APIException::notFound);

        user.setMerchantId(request.getMerchantId());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRoles() != null) {
            Set<Role> roles = request.getRoles().stream().map(roleService::getRole).collect(Collectors.toSet());
            user.setRoles(roles);
        }

        User savedUser = repository.save(user);

        LOGGER.info("Successfully updated user id: {}, username: {}", id, user.getUsername());

        return new SuccessResponse(savedUser);
    }

    @Transactional
    public void updateUserStatus(Long id, Integer status) {
        User user = repository.findById(id).orElse(null);

        if (user != null) {
            user.setStatus(status);
            repository.save(user);
            LOGGER.info("Successfully update user status: id={}, status={}", id, status);
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = repository.findById(id).orElseThrow(APIException::notFound);
        user.setDeletedAt(new Date());

        repository.save(user);

        LOGGER.info("Successfully delete user id: {}", id);
    }

    public String generateReferralId() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getReferralId() != null && !currentUser.getReferralId().isEmpty()) {
            return currentUser.getReferralId() + "-" + currentUser.getUserId();
        } else {
            return "REF-" + currentUser.getUserId(); // fallback
        }
    }
}
