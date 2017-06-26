package com.zhtian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Skye on 2017/6/24.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    int countByUsername(String username);

    @Query(value = "select count(*)+1 from user u where u.maxscore > ?1", nativeQuery = true)
    int countRanking(int maxscore);

    User findByUsername(String username);

    List<User> findFirst10ByOrderByMaxscoreDesc();

    @Modifying
    @Query(value = "update user u set u.maxscore = ?1 where u.username = ?2", nativeQuery = true)
    void updateMaxScore(int maxscore, String username);

}
