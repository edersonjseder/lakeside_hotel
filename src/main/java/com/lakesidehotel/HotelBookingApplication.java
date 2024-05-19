package com.lakesidehotel;

import com.lakesidehotel.exceptions.UserException;
import com.lakesidehotel.models.User;
import com.lakesidehotel.repositories.UserRepository;
import com.lakesidehotel.utils.UserUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import static com.lakesidehotel.constants.UserMessagesConstants.USERNAME_ALREADY_EXISTS_MESSAGE;
import static com.lakesidehotel.constants.UserMessagesConstants.USER_EMAIL_ALREADY_EXISTS_MESSAGE;

@Log4j2
@SpringBootApplication
@EnableTransactionManagement
public class HotelBookingApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserUtils userUtils;

	@Value("${webmaster.username}")
	private String webmasterUsername; // gets username value from .properties file.

	@Value("${webmaster.password}")
	private String webmasterPassword; // gets password value from .properties file.

	@Value("${webmaster.email}")
	private String webmasterEmail; // gets email value from .properties file.

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		User user;
		try {
			if (userRepository.existsByUsername(webmasterUsername)) {
				throw new UserException(USERNAME_ALREADY_EXISTS_MESSAGE + webmasterUsername);
			}
			user = userUtils.createAdminUser(webmasterUsername, webmasterEmail, webmasterPassword);
			log.debug("Creating user with username: {}", user.getUsername());
			userRepository.save(user);
			log.info("User {} created", user.getUsername());
		} catch (UserException e) {
			log.error(e.getMessage());
		}
	}
}
