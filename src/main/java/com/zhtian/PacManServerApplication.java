package com.zhtian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@SpringBootApplication
@RestController
public class PacManServerApplication {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PacManServerApplication.class);
		app.setWebEnvironment(true);
		app.run(args);
	}

	@PostMapping(value = "/regist")
	public ResponseEntity<?> regist(String username, String password) {
	    int cnt = userRepository.countByUsername(username);
	    if (cnt > 0) {
	        return new ResponseEntity<>("用户名已存在", HttpStatus.NOT_FOUND);
        }
        userRepository.save(new User(username, password, 0));
		return new ResponseEntity<>("注册成功", HttpStatus.OK);
	}

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(HttpSession session, String username, String password) {
        logger.info(session.getId());
        int cnt = userRepository.countByUsername(username);
        if (cnt <= 0) {
            return new ResponseEntity<>("用户名不存在", HttpStatus.NOT_FOUND);
        }
        String temp = userRepository.getUserByUsername(username).getPassword();
        if (!temp.equals(password)) {
            return new ResponseEntity<>("密码错误", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("登陆成功", HttpStatus.OK);
    }

}
