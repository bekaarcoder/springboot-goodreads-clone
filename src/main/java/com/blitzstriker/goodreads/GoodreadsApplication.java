package com.blitzstriker.goodreads;

import com.blitzstriker.goodreads.config.AppConstants;
import com.blitzstriker.goodreads.entity.Role;
import com.blitzstriker.goodreads.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class GoodreadsApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public GoodreadsApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(GoodreadsApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            var role1 = new Role();
            role1.setId(AppConstants.ROLE_ADMIN);
            role1.setName("ROLE_ADMIN");

            var role2 = new Role();
            role2.setId(AppConstants.ROLE_USER);
            role2.setName("ROLE_USER");

            List<Role> roles = List.of(role1, role2);
            roleRepository.saveAll(roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
