package pl.movie.registration.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.movie.registration.dto.UserData;
import pl.movie.registration.exception.UserAlreadyExistException;
import pl.movie.registration.model.Role;
import pl.movie.registration.model.User;
import pl.movie.registration.repository.RoleRepository;
import pl.movie.registration.repository.UserRepository;
import pl.movie.registration.service.RoleService;
import pl.movie.registration.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(UserData userData) {
        User user = populateUserData(userData);

        Role role = roleService.findByName("USER");
        List<Role> roleSet = new ArrayList<>();
        roleSet.add(role);

//TODO sprawdzić poprawność funkcji
//        if(user.getEmail().split("@")[1].equals("admin")){
//            role = roleService.findByName("ADMIN");
//            roleSet.add(role);
//        }
        user.setRoles(roleSet);

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

        Role userRole = roleService.findByName("USER");
        userEntity.setRoles(Collections.singletonList(userRole));

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
