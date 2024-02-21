package com.backendspring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.backendspring.enums.Category;
import com.backendspring.model.Course;
import com.backendspring.model.Lesson;
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

			List<Lesson> lessons = new ArrayList<>();
			Lesson l1 = new Lesson();
			l1.setName("Aula 01 curso spring");
			l1.setYoutubeUrl("youtube.com");
			l1.setCourse(c);

			Lesson l2 = new Lesson();
			l2.setName("Aula 02 curso spring");
			l2.setYoutubeUrl("youtube.com");
			l2.setCourse(c);

			Lesson l3 = new Lesson();
			l3.setName("Aula 03 curso spring");
			l3.setYoutubeUrl("youtube.com");
			l3.setCourse(c);
			
			lessons.add(l1);
			lessons.add(l2);
			lessons.add(l3);

			c.setLessons(lessons);

			courseRepository.save(c);
		};
    }

}
