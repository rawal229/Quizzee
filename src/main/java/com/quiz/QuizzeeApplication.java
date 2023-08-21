package com.quiz;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.quiz.helper.UserFoundException;
import com.quiz.model.Role;
import com.quiz.model.User;
import com.quiz.model.UserRole;
import com.quiz.repository.QuizRepository;
import com.quiz.service.UserService;

@SpringBootApplication
public class QuizzeeApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public QuizRepository quizRepository;

	public static void main(String[] args) {
		SpringApplication.run(QuizzeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("starting code");

		try {

			User user = new User();

			user.setFirstName("tech");
			user.setLastName("nishu");
			user.setUsername("technishu");
			user.setPassword(this.bCryptPasswordEncoder.encode("nishu"));
			user.setEmail("nishu@gmail.com");
			user.setProfile("default.png");

			Role role1 = new Role();
			role1.setRoleId(44L);
			role1.setRoleName("ADMIN");

			Set<UserRole> userRoleSet = new HashSet<>();
			UserRole userRole = new UserRole();
			userRole.setRole(role1);
			userRole.setUser(user);

			userRoleSet.add(userRole);

			User user1 = this.userService.createUser(user, userRoleSet);
			System.out.println(user1.getUsername());

		} catch (UserFoundException e) {
			e.printStackTrace();
		}
	}
}
