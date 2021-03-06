package com.zhtian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootApplication
@RestController
@Transactional
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
    public ResponseEntity<?> login(String username, String password) {
        int cnt = userRepository.countByUsername(username);
        if (cnt <= 0) {
            return new ResponseEntity<>("用户名不存在", HttpStatus.NOT_FOUND);
        }
        String temp = userRepository.findByUsername(username).getPassword();
        if (!temp.equals(password)) {
            return new ResponseEntity<>("密码错误", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userRepository.findByUsername(username).getMaxscore() + "", HttpStatus.OK);
    }

    @GetMapping(value = "/rank/{username}")
    public ResponseEntity<?> rank(@PathVariable String username) {
        List<User> userList = userRepository.findFirst10ByOrderByMaxscoreDesc();
        StringBuilder result = new StringBuilder("|");
        int cnt = 1;
        for (User i : userList) {
            result.append("玩家" + i.getUsername() + "的分数是" + i.getMaxscore() + "，世界排名是第" + (cnt++) + "位|");
        }
        User temp = userRepository.findByUsername(username);
        int ranking = userRepository.countRanking(temp.getMaxscore());
        return new ResponseEntity<>(new RankForm(username, temp.getMaxscore(), ranking, new String(result)), HttpStatus.OK);
    }

    @PostMapping(value = "/submit")
    public ResponseEntity<?> submit(String username, String score) {
        userRepository.updateMaxScore(Integer.parseInt(score), username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
