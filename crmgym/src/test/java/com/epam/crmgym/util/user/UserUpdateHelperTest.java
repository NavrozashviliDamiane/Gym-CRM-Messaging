package com.epam.crmgym.util.user;

import com.epam.crmgym.dto.trainer.TrainerUpdateDTO;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.service.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserUpdateHelperTest {



    @Test
    public void testUpdateUser_NullTrainerUpdateDTO() {
        UserService userService = mock(UserService.class);
        UserUpdateHelper userUpdateHelper = new UserUpdateHelper();

        User user = new User();
        TrainerUpdateDTO trainerUpdateDTO = null;

        assertThrows(NullPointerException.class, () -> {
            userUpdateHelper.updateUser(user, trainerUpdateDTO);
        });
    }

    @Test
    public void testUpdateUser_NullFirstName() {
        UserService userService = mock(UserService.class);
        UserUpdateHelper userUpdateHelper = new UserUpdateHelper();

        User user = new User();
        TrainerUpdateDTO trainerUpdateDTO = new TrainerUpdateDTO();
        trainerUpdateDTO.setLastName("Doe");
        trainerUpdateDTO.setIsActive(true);

        assertThrows(NullPointerException.class, () -> {
            userUpdateHelper.updateUser(user, trainerUpdateDTO);
        });
    }

    @Test
    public void testUpdateUser_NullLastName() {
        UserService userService = mock(UserService.class);
        UserUpdateHelper userUpdateHelper = new UserUpdateHelper();

        User user = new User();
        TrainerUpdateDTO trainerUpdateDTO = new TrainerUpdateDTO();
        trainerUpdateDTO.setFirstName("John");
        trainerUpdateDTO.setIsActive(true);

        assertThrows(NullPointerException.class, () -> {
            userUpdateHelper.updateUser(user, trainerUpdateDTO);
        });
    }
}
