package pl.movie.registration.service;

import pl.movie.registration.dto.UserData;
import pl.movie.registration.exception.UserAlreadyExistException;
import pl.movie.registration.model.User;

public interface UserService {
    User save(UserData userData);

    void register(UserData userData) throws UserAlreadyExistException;

    boolean checkIfUserExist(String email);

    User findUserByUsername(String email);
}
