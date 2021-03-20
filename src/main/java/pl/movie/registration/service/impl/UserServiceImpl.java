package pl.movie.registration.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.movie.registration.dto.UserData;
import pl.movie.registration.exception.UserAlreadyExistException;
import pl.movie.registration.model.User;
import pl.movie.registration.repository.UserRepository;
import pl.movie.registration.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(UserData userData) {
        User user = populateUserData(userData);
        return userRepository.save(user);
    }

    private User populateUserData(final UserData userData) {
        User user = new User();
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        return user;
    }

    @Override
    public void register(UserData userData) throws UserAlreadyExistException {

        if (checkIfUserExist(userData.getEmail())) {
            throw new UserAlreadyExistException("User already exists for this email");
        }

        User userEntity = new User();
        BeanUtils.copyProperties(userData, userEntity);
        encodePassword(userEntity, userData);
        userRepository.save(userEntity);
    }

    @Override
    public boolean checkIfUserExist(String email) {
        User findUser = userRepository.findByEmail(email);
        return findUser != null;
    }

    @Override
    public User findUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    private void encodePassword(User userEntity, UserData userData) {
        userEntity.setPassword(passwordEncoder.encode(userData.getPassword()));
    }

}
