package rs.ac.bg.fon.silab.dms.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.model.User;
import rs.ac.bg.fon.silab.dms.core.repository.UserRepository;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws BadRequestException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new BadRequestException("User with given username already exists.");
        }
        return userRepository.saveAndFlush(user);
    }
}
