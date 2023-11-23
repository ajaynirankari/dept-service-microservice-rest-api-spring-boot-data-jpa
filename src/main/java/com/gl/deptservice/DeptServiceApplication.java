package com.gl.deptservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DeptServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeptServiceApplication.class, args);
	}

}

@RestController
class DeptController {
	DeptRepo repo;

	public DeptController(DeptRepo repo) {
		this.repo = repo;
	}

	@GetMapping("/depts")
	public List<Dept> all() {
		return repo.findAll();
	}

	@GetMapping("/depts/{id}")
	public Optional<Dept> one(@PathVariable long id) {
		return repo.findById(id);
	}
}

interface DeptRepo extends JpaRepository<Dept, Long> {
}

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
class Dept {
	@Id
	@GeneratedValue
	long id;
	String name;
}

@Configuration
class LoadBasicData {
	@Bean
	CommandLineRunner loadInitData(DeptRepo repo) {
		return args -> {
			List<Dept> depts = new ArrayList<>();
			depts.add(Dept.builder().name("IT").build());
			depts.add(Dept.builder().name("CS").build());
			depts.add(Dept.builder().name("Finance").build());
			depts.add(Dept.builder().name("IOT").build());
			depts.add(Dept.builder().name("AI").build());
			depts.add(Dept.builder().name("General").build());

			repo.saveAll(depts);
		};
	}
}
