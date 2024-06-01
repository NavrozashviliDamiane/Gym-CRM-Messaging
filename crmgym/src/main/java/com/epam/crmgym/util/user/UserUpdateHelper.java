package com.epam.crmgym.util.user;

import com.epam.crmgym.dto.trainer.TrainerUpdateDTO;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateHelper {

    @Autowired
    private UserService userService;

    public User updateUser(User user, TrainerUpdateDTO trainerUpdateDTO) {
        user.setFirstName(trainerUpdateDTO.getFirstName());
        user.setLastName(trainerUpdateDTO.getLastName());
        user.setActive(trainerUpdateDTO.getIsActive());
        return userService.saveUser(user);
    }
}
