package rs.ac.bg.fon.silab.dms.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.model.Company;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.repository.UserRepository;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.util.StringUtils;

import static rs.ac.bg.fon.silab.dms.util.StringUtils.isStringEmptyOrNull;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserService() {
    }

    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.encoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws BadRequestException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new BadRequestException("User with given username already exists.");
        }
        if (isStringEmptyOrNull(user.getPassword()) || user.getRole() == null || user.getCompany() == null) {
            throw new IllegalStateException("User is in a bad state.");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
