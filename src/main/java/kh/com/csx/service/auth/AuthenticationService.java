package kh.com.csx.service.auth;

import kh.com.csx.dto.MerchantDTO;
import kh.com.csx.dto.UserDTO;
import kh.com.csx.dto.VendorDTO;
import kh.com.csx.entity.User;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.MerchantRepository;
import kh.com.csx.repository.UserRepository;
import kh.com.csx.request.AuthenticationRequest;
import kh.com.csx.request.RegisterRequest;
import kh.com.csx.response.AuthenticationResponse;
import kh.com.csx.response.Response;
import kh.com.csx.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository useRepository;
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Response register(RegisterRequest request) {
        User existedUser = useRepository.findByUsername(request.getUsername()).orElse(null);
        if (existedUser != null) {
            throw new APIException("Username already exists", HttpStatus.CONFLICT.value());
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        useRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        LOGGER.info("registered user : {}", user.getUsername());
        return new SuccessResponse(HttpStatus.CREATED.value(), AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build());
    }

    public Response authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = useRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        List<MerchantDTO> merchants = List.of();

        if (user.getVendorId() != null && user.getMerchantId() != null) {
            merchants = List.of(MerchantDTO.toAuthDTO(Objects.requireNonNull(merchantRepository.findById(user.getMerchantId()).orElse(null))));
        } else if (user.getVendorId() != null) {
            merchants = merchantRepository.findByVendorId(user.getVendorId())
                    .stream()
                    .map(MerchantDTO::toAuthDTO)
                    .collect(Collectors.toList());
        }

        UserDTO userDTO = UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .merchantId(user.getMerchantId())
                .vendorId(user.getVendorId())
                .isBaseOwner(user.getIsBaseOwner())
                .referralId(user.getReferralId())
                .vendor(user.getVendor() != null ? VendorDTO.toAuthDTO(user.getVendor()) : null)
                .roles(user.getRoles())
                .merchants(merchants)
                .authorities(user.getAuthorities())
                .build();

        AuthenticationResponse authenticationResponse = AuthenticationResponse
                .builder()
                .token(jwtToken)
                .user(userDTO)
                .build();

        LOGGER.info("logged user : {}", userDTO.getUsername());

        return new SuccessResponse(
                HttpStatus.OK.value(),
                "Login Successfully",
                authenticationResponse
        );
    }
}
