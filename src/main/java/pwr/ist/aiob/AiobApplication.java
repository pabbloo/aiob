package pwr.ist.aiob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import pwr.ist.aiob.model.User;
import pwr.ist.aiob.repository.UserRepository;

import javax.annotation.Resource;

@SpringBootApplication
public class AiobApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AiobApplication.class, args);
	}

	@Resource
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.save(new User("user", passwordEncoder.encode("user1"), "user@gmail.com", "USER", "MSINR4NEOGG6LD7FSXKIHVVRIGMB4BYB"));
	}
}
