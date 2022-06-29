package com.vart.psychoweb.service;

import com.vart.psychoweb.model.security.User;

public interface UserService {
    User create (Integer phoneNumber, String email);
    User findById(long id);
    User update (User user);
    boolean delete (long id);

    boolean checkExistingByPhoneNumber(Integer phoneNumber);

    boolean checkExistingByEmail (String email);

    User findByPhoneNumberOrEmail(Integer phoneNumber, String email);
}
