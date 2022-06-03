package pwr.ist.aiob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pwr.ist.aiob.model.EmployeeDAO;
import pwr.ist.aiob.repository.EmployeeRepository;

@Configuration
class PreloadDatabase {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreloadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        return args -> {
            EmployeeDAO employee1 = new EmployeeDAO("Janusz", "Kowalski", "99999999999", 10000, "CEO", "HQ");
            EmployeeDAO employee2 = new EmployeeDAO("Piotr", "Nowak", "22222222222", 5000, "Developer", "Software Development");
            EmployeeDAO employee3 = new EmployeeDAO("Marcin", "Dudecki", "33333333333", 20000, "Junior Developer", "Software Development");

            LOGGER.info("Preloading " + repository.save(employee1));
            LOGGER.info("Preloading " + repository.save(employee2));
            LOGGER.info("Preloading " + repository.save(employee3));
        };
    }
}
