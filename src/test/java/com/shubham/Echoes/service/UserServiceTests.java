package com.shubham.Echoes.service;

import com.shubham.Echoes.entity.User;
import com.shubham.Echoes.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // -----------------------------------------
    // Test saveNewUser using ArgumentsProvider
    // -----------------------------------------
    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        // Save user (void method)
        userService.saveNewUser(user);

        // Verify it was saved
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(savedUser, "User should be saved in the repository");
        assertEquals(user.getUserName(), savedUser.getUserName(), "Username should match");
        assertEquals(user.getPassword(), savedUser.getPassword(), "Password should match");
    }

    // -----------------------------------------
    // Simple CSV test example
    // -----------------------------------------
    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void testAddition(int a, int b, int expected){
        assertEquals(expected, a + b, "Sum should match expected value");
    }
}
