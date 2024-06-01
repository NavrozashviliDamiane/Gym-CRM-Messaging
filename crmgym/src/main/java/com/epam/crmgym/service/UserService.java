package com.epam.crmgym.service;


import com.epam.crmgym.dto.user.ChangePasswordRequest;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.exception.UnauthorizedAccessException;
import com.epam.crmgym.exception.UsernameValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserService {


    User createUser(String firstName, String lastName) throws UsernameValidationException;

    @Transactional
    void deleteUserById(Long userId);

    User saveUser(User user);

    void changePassword(ChangePasswordRequest request) throws UnauthorizedAccessException;

}
