package pl.movie.registration.service;

import pl.movie.registration.model.Role;

public interface RoleService {

    Role findByName(String name);

}
