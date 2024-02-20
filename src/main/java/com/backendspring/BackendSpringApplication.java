package com.backendspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.backendspring.enums.Category;
import com.backendspring.model.Course;
import com.backendspring.repository.CourseRepository;

@SpringBootApplication
public class BackendSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSpringApplication.class, args);
	}

    @Bean
    CommandLineRunner initDataBase(CourseRepository courseRepository) {
        return args -> {
			courseRepository.deleteAll();
			Course c = new Course();
			c.setName("Spring");
			c.setCategory(Category.BACK_END);
			courseRepository.save(c);
		};
    }

}
