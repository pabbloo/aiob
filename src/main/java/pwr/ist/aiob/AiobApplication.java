package pwr.ist.aiob;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.save(new User("user", "{noop}user1", "user@gmail.com", "USER", "MSINR4NEOGG6LD7FSXKIHVVRIGMB4BYB"));
	}
}
