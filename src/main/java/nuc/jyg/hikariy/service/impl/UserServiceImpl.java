package nuc.jyg.hikariy.service.impl;

import nuc.jyg.hikariy.dao.UserRepository;
import nuc.jyg.hikariy.model.User;
import nuc.jyg.hikariy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * @author Ji YongGuang.
 * @date 20:21 2019-04-23.
 * @description
 */
@Service(value = "iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User find(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByIdAndPassword(Integer id, String password) {
        return userRepository.findByIdAndPassword(id, password);
    }

    @Override
    public User updatePassword(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public String findQuestionByEmail(String email) {
        return userRepository.findByEmail(email).getQuestion();
    }

    @Override
    public Boolean checkQuestionAnswer(String email, String answer) {
        User user = userRepository.findByEmailAndAnswer(email, answer);
        if (Objects.nonNull(user)) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean modifyPassword(String email, String passwordNew) {
        User user = userRepository.findByEmail(email);
        Integer result = userRepository.updatePassword(user.getId(), passwordNew);
        return result > 0 ? true : false;
    }

}
