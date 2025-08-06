package kh.com.csx.config;

import kh.com.csx.entity.Role;
import kh.com.csx.entity.User;
import kh.com.csx.repository.RoleRepository;
import kh.com.csx.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DatabaseSeeder {
    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Check if role exists, if not create
            Role role = roleRepository.findById(3)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setRoleId(3);
                        newRole.setName("SUPER_ADMIN");
                        return roleRepository.save(newRole);
                    });

            // Check if user exists, if not create
            userRepository.findByUsername("super_admin")
                    .orElseGet(() -> {
                        User user = new User();
                        user.setUsername("super_admin");
                        user.setPassword(passwordEncoder.encode("Dev@123456"));
                        user.setStatus(1);
                        user.setRoles(Set.of(role));
                        return userRepository.save(user);
                    });
        };
    }
}
