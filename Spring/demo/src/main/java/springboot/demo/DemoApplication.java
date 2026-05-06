package springboot.demo;

import java.util.Arrays;
import java.beans.BeanProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import springboot.demo.User;
import springboot.demo.UserRepository;
import lombok.extern.log4j.Log4j2;



@SpringBootApplication
@Log4j2
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.debug("Beans:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			Arrays.stream(beanNames).forEach(beanName -> log.debug(beanName));

			var passwordEncoder = ctx.getBean(PasswordEncoder.class);
			var userRepository = ctx.getBean(UserRepository.class);

			userRepository.save(new User("user", passwordEncoder.encode("password"), "first", "last"));

			userRepository.save(new User("user1", passwordEncoder.encode("password1"), "first1", "last1"));
			
			log.debug("Currents=====");
			userRepository.findAll().forEach(customer -> {
                log.debug(customer);
            });
		};
	}

}
