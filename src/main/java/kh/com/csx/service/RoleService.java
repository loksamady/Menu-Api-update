package kh.com.csx.service;

import kh.com.csx.entity.Role;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static kh.com.csx.constant.RoleConstant.*;

@Service
public class RoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    private RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getRoles() {
        Set<String> owner = Set.of(String.format("ROLE_%s", OWNER));
        Set<String> admin = Set.of(String.format("ROLE_%s", ADMIN));

        if (hasAnyRole(owner)) return repository.findByNameIn(List.of(OWNER, ADMIN, STAFF));

        if (hasAnyRole(admin)) return repository.findByNameIn(List.of(ADMIN, STAFF));

        return repository.findAll();
    }

    public Role getRole(Integer id) {
        Role role = repository.findById(id).orElseThrow(APIException::notFound);

        LOGGER.info("Successfully get role: {}", role);

        return role;
    }

    public Boolean hasAnyRole(Set<String> roleNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(roleNames::contains);
        }

        return false;
    }
}