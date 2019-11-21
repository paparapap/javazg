package com.bellabeat.javazg.testcon;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SpringBootApplication
@EnableJpaRepositories
public class TestconApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestconApplication.class, args);
	}

}

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
class User	{
	@Id private String id;
}

interface UserRepository extends JpaRepository<User, String> {}

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController	{
	private final UserRepository userRepository;

	@PutMapping
	public User put(@RequestBody User user)	{
		return userRepository.save(user);
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable String id)	{
		return userRepository.findById(id).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
	}
}
