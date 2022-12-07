package com.blitzstriker.goodreads;

import com.blitzstriker.goodreads.config.AppConstants;
import com.blitzstriker.goodreads.entity.*;
import com.blitzstriker.goodreads.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class GoodreadsApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;
    private final ShelfRepository shelfRepository;
    private final BookShelfRepository bookShelfRepository;
    private final UserBookRepository userBookRepository;

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

            /*User gwen = userRepository.findById(1L).get();
            User tony = userRepository.findById(2L).get();

            Book b1 = bookRepository.findById(1L).get();
            Book b2 = bookRepository.findById(2L).get();
            Book b3 = bookRepository.findById(3L).get();
            Book b4 = bookRepository.findById(4L).get();*/

            /*List<Shelf> gwenShelf = shelfRepository.findShelfByUser(gwen);
            gwenShelf.forEach(shelf -> {
                System.out.println(shelf.getName());
                bookShelfRepository.findBookShelfByShelf(shelf).forEach(bookShelf -> System.out.println(bookShelf.getBook().getName()));
            });*/

            /*Shelf shelf = shelfRepository.findById(1L).get();
            shelfRepository.delete(shelf);*/

            User tony = userRepository.findById(2L).get();
            List<UserBook> userBooks =userBookRepository.findUserBookByUser(tony);
            userBooks.forEach(userBook -> {
                System.out.println(userBook.getBook().getName());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
