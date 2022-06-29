package com.vart.psychoweb.service.impl;

import com.vart.psychoweb.exception.RoleNotFoundException;
import com.vart.psychoweb.exception.UserExistingException;
import com.vart.psychoweb.exception.UserNullDataException;
import com.vart.psychoweb.model.security.User;
import com.vart.psychoweb.repository.RoleRepository;
import com.vart.psychoweb.repository.UserRepository;
import com.vart.psychoweb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import static com.vart.psychoweb.utils.Constants.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    @Transactional
    @Override
    public User create(Integer phoneNumber, String email) {
        if (phoneNumber == null && email == null) throw new UserNullDataException(USER_DATA_NULL_EXCEPTION);
        if (phoneNumber != null && checkExistingByEmail(email)) {
            throw new UserExistingException(USER_EMAIL_EXISTING_ERROR + email);
        }
        if (email != null && checkExistingByPhoneNumber(phoneNumber)) {
            throw new UserExistingException(USER_PHONE_EXISTING_ERROR + phoneNumber);
        }
        String salt = String.valueOf(random.nextInt(99999));
        String ident = phoneNumber == null ? email : phoneNumber.toString();
        User newUser = User.builder()
                .username(salt + "_" + ident)
                .password(passwordEncoder.encode(salt + "_" + ident))
                .roles(Set.of(roleRepository.findByName("customer").orElseThrow(
                        () -> new RoleNotFoundException(ROLE_NOT_FOUND_EXCEPTION + "customer"))))
                .email(email)
                .phoneNumber(phoneNumber)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION + " id: " + id));
    }

    @Transactional
    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean delete(long id) {
        userRepository.delete(findById(id));
        return true;
    }

    @Override
    public boolean checkExistingByPhoneNumber(Integer phoneNumber) {
        return userRepository.findByPhoneNumber(Objects.requireNonNull(phoneNumber)).isPresent();
    }

    @Override
    public boolean checkExistingByEmail(String email) {
        return userRepository.findByEmail(Objects.requireNonNull(email)).isPresent();
    }

    @Override
    public User findByPhoneNumberOrEmail(Integer phoneNumber, String email) {
        if (phoneNumber == null && email == null) throw new UserNullDataException(USER_DATA_NULL_EXCEPTION);
        if (phoneNumber == null && checkExistingByEmail(email)) {
            return userRepository.findByEmail(Objects.requireNonNull(email)).orElseThrow(
                    () -> new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION + " email: " + email));
        } else if (email == null && checkExistingByPhoneNumber(phoneNumber)) {
            return userRepository.findByPhoneNumber(Objects.requireNonNull(phoneNumber)).orElseThrow(
                    () -> new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION + " phone number: " + phoneNumber));
        } else if (checkExistingByPhoneNumber(phoneNumber) && checkExistingByEmail(email)) {
            User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                    () -> new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION + " phone number: " + phoneNumber));
            if (user.getEmail().equals(email)) return user;
        }
        return null;
    }
}
