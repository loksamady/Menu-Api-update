package kh.com.csx.controller.auth;

import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.entity.User;
import kh.com.csx.request.AuthenticationRequest;
import kh.com.csx.request.RegisterRequest;
import kh.com.csx.request.UpdateUserRequest;
import kh.com.csx.response.Response;
import kh.com.csx.service.UserService;
import kh.com.csx.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthenticationService service;

    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public Response register(@Valid @RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PutMapping("/profile")
    public Response updateProfile(@Valid @RequestBody UpdateUserRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = currentUser.getUserId();

        return userService.updateUser(userId, request);
    }

    @PostMapping("/login")
    public Response login(@Valid @RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @GetMapping("/login")
    public Response test(@Valid @RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }
}
