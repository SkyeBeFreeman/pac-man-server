package com.zhtian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PacManServerApplication {

    @Autowired
    private UserDao userDao;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PacManServerApplication.class);
		app.setWebEnvironment(true);
		app.run(args);
	}

	@PostMapping(value = "/regist")
	public ResponseEntity<?> regist(String username, String password) {
	    int cnt = userDao.getNameCount(username);
	    if (cnt > 0) {
	        return new ResponseEntity<>("用户名已存在", HttpStatus.OK);
        }
        userDao.save(new User(username, password, 0));
		return new ResponseEntity<>("注册成功", HttpStatus.OK);
	}

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(String username, String password) {
        int cnt = userDao.getNameCount(username);
        if (cnt <= 0) {
            return new ResponseEntity<>("用户名不存在", HttpStatus.OK);
        }
        String temp = userDao.getPasswordByName(username);
        if (!temp.equals(password)) {
            return new ResponseEntity<>("密码错误", HttpStatus.OK);
        }
        return new ResponseEntity<>("登陆成功", HttpStatus.OK);
    }

}
