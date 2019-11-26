package nuc.jyg.hikariy.dao;

import nuc.jyg.hikariy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Ji YongGuang.
 * @date 18:37 2019-04-23.
 * @description
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByIdAndPassword(Integer id, String password);

    User findByEmailAndPassword(String email, String password);

    @Query(nativeQuery = true, value = "update user set password = :newPassword where id = :id")
    @Modifying
    Integer updatePassword(Integer id, String newPassword);

    User findByEmail(String email);

    User findByEmailAndAnswer(String email, String answer);

}
