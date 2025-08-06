package kh.com.csx.security;

import kh.com.csx.exception.APIException;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static kh.com.csx.constant.RoleConstant.SUPER_ADMIN;

@Aspect
@Component
@AllArgsConstructor
public class RoleOrPermissionsAspect {
    @Before("@annotation(RolesOrPermissions)")
    public void beforeAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RolesOrPermissions rolesOrPermissions = signature.getMethod().getAnnotation(RolesOrPermissions.class);
        String[] allowAccessRoles = rolesOrPermissions.roles();
        String[] allowAccessPermissions = rolesOrPermissions.permissions();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            boolean rolesAccessGrant = checkRoles(userDetails, allowAccessRoles);
            boolean permissionsAccessGrant = checkPermissions(userDetails, allowAccessPermissions);

            if (!rolesAccessGrant && !permissionsAccessGrant) {
                throw new APIException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value());
            }

        } else {
            throw new APIException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value());
        }

    }

    /**
     * This method will check any role matching and role hierarchy
     *
     * @param userDetails      for access user granted authorities
     * @param allowAccessRoles list of allowed roles to check
     * @return return true if role match or role is higher roleHierarchy
     */
    private boolean checkRoles(UserDetails userDetails, String[] allowAccessRoles) {
        var authorities = AuthorityUtils.createAuthorityList(
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new)
        );

        // Always allow if user has SUPER_ADMIN role
        if (authorities.stream().anyMatch(auth -> String.format("ROLE_%s", SUPER_ADMIN).equals(auth.getAuthority()))) {
            return true;
        }

        // Otherwise, check against the allowed roles
        return authorities.stream()
                .anyMatch(grantedAuthority -> java.util.Arrays.stream(allowAccessRoles).anyMatch(
                        role -> grantedAuthority.getAuthority().equals(String.format("ROLE_%s", role))
                ));
    }

    /**
     * This method will check any matching permission
     *
     * @param userDetails            for access user grated authorities
     * @param allowAccessPermissions list of allowed permission to check
     * @return return true only if any permission match
     */
    private boolean checkPermissions(UserDetails userDetails, String[] allowAccessPermissions) {
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> java.util.Arrays.stream(allowAccessPermissions)
                        .anyMatch(permission -> grantedAuthority.getAuthority().equals(permission))
                );
    }
}
