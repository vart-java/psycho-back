package com.vart.psychoweb.utils;

import com.vart.psychoweb.model.security.Authority;
import com.vart.psychoweb.model.security.Role;
import com.vart.psychoweb.model.security.User;
import com.vart.psychoweb.repository.AuthorityRepository;
import com.vart.psychoweb.repository.RoleRepository;
import com.vart.psychoweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
public class InitializeUserSecurityDataLoader implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.username:admin}")
    private String username;
    @Value("${admin.password:admin}")
    private String password;

    @Override
    public void run(String... args) {
        loadAuthorityInDb("create");
        loadAuthorityInDb("read");
        loadAuthorityInDb("update");
        loadAuthorityInDb("delete");

        loadRoleInDb("admin", "create", "read", "update", "delete");
        loadRoleInDb("customer", "create", "read", "update");
        loadRoleInDb("user", "read");

        loadUserInDb(username, password, "admin");
        loadUserInDb("customer", "customer", "customer");
        loadUserInDb("user", "user", "user");

    }

    @Transactional
    void loadAuthorityInDb(String permission) {
        if (authorityRepository.findByPermission(permission).isPresent()) {
            return;
        }
        authorityRepository.save(Authority.builder().permission(permission).build());
        log.debug("Authority " + permission + " loaded");
    }

    @Transactional
    void loadRoleInDb(String name, String... permissions) {
        if (roleRepository.findByName(name).isPresent()) {
            return;
        }
        roleRepository.save(Role.builder().name(name).authorities(Stream.of(permissions)
                        .map(permission -> authorityRepository.findByPermission(permission)
                                .orElseThrow(() -> new EntityNotFoundException("Permission " + permission + " not found")))
                        .collect(Collectors.toSet()))
                .build());
        log.debug("Role " + name + " loaded");
    }

    @Transactional
    void loadUserInDb(String loadUsername, String loadPassword, String... roles) {
        Optional<User> userOptional = userRepository.findByUsername(loadUsername);
        if (userOptional.isPresent() // if user is present and not admin
                && !loadUsername.equals(username)) {
            return;
        } else if (loadUsername.equals(username) && userOptional.isPresent()) { // is user admin - we must update him
            User user = userOptional.get();
            user.setUsername(loadUsername);
            user.setPassword(passwordEncoder.encode(loadPassword));
            user.setRoles(Stream.of(roles).map(role ->
                            roleRepository.findByName(role)
                                    .orElseThrow(() -> new EntityNotFoundException("Role " + role + " not found")))
                    .collect(Collectors.toSet()));
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
            userRepository.save(user);
        } else { // any other user

            userRepository.save(User.builder()
                    .username(loadUsername)
                    .password(passwordEncoder.encode(loadPassword))
                    .roles(Stream.of(roles).map(role ->
                                    roleRepository.findByName(role)
                                            .orElseThrow(() -> new EntityNotFoundException("Role " + role + " not found")))
                            .collect(Collectors.toSet()))
                    .build());
            log.debug("User " + loadUsername + " with role " + roles + " loaded");
        }
    }
}
