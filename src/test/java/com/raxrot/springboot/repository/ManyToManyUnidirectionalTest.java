package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.MyTests.Role;
import com.raxrot.springboot.entity.MyTests.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ManyToManyUnidirectionalTest {

    @Autowired
    private UserRepository userRepository;
    private Role role;
    private User user;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setName("ROLE_USER");

        user = new User();
        user.setEmail("raxrot@gmail.com");
        user.setPassword("password");
        user.setFirstName("Vlad");
        user.setLastName("Bulahov");
        user.getRoles().add(role);
    }

    @AfterEach
    public void teardown() {
        userRepository.deleteAll();
    }

    @Test
    public void saveTest() {
        userRepository.save(user);
        assertThat(userRepository.findById(user.getId())).isNotNull();
    }

    @Test
    public void findTest() {
        userRepository.save(user);
        User found = userRepository.findById(user.getId()).get();
        assertThat(found.getFirstName()).isEqualTo(user.getFirstName());
        Role role = user.getRoles().stream()
                .findFirst()
                .orElse(null);
        assertThat(role.getName()).isEqualTo("ROLE_USER");
    }

    @Test
    public void updateTest() {
        userRepository.save(user);
        User found = userRepository.findById(user.getId()).get();
        Role role = found.getRoles().stream()
                .findFirst()
                .orElse(null);

        found.setFirstName("Lol");
        role.setName("ROLE_ADMIN");

       User updated = userRepository.save(found);
       assertThat(updated.getFirstName()).isEqualTo("Lol");
        Role updatedRole = user.getRoles().stream()
                .findFirst()
                .orElse(null);
        assertThat(updatedRole.getName()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    public void deleteTest() {
        userRepository.save(user);
        User found = userRepository.findById(user.getId()).get();
        found.getRoles().clear();
        userRepository.save(found);
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}
