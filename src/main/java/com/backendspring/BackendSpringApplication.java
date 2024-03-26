package com.backendspring;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.backendspring.enums.Category;
import com.backendspring.enums.Status;
import com.backendspring.model.Course;
import com.backendspring.model.Lesson;
import com.backendspring.repository.CourseRepository;

@SpringBootApplication
public class BackendSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSpringApplication.class, args);
	}

    @Bean
	@Profile("dev")
    CommandLineRunner initDataBase(CourseRepository courseRepository) {
        return args -> {
			courseRepository.deleteAll();
			for (int i = 1; i < 50; i++) {
				Course c = new Course();
				c.setName("Course " + i);
				c.setCategory(Category.FRONT_END);
				c.setStatus(Status.ACTIVE);
	
				for (int j = 1; j < 10; j++) {
					Lesson lesson = new Lesson();
					lesson.setName("Lesson " + j);
					lesson.setYoutubeUrl("Fj3Zvf-N4bk");
					c.addLesson(lesson);
				}
	
				courseRepository.save(c);
			}

		};
    }

}
