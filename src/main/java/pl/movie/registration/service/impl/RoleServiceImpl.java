package pl.movie.registration.service.impl;

import org.springframework.stereotype.Service;
import pl.movie.registration.model.Role;
import pl.movie.registration.repository.RoleRepository;
import pl.movie.registration.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
