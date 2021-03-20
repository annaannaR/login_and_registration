package pl.movie.registration.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("pass"));

        System.out.println(encoder.matches("pass", "$2a$10$pjv4Kep8.lERXeR6XFFTvuHnOtaJa3l5HFQKcNJqmCj8HyHknEKwC"));

    }
}
