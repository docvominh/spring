package com.vominh.example.spring.mongo.config.security;

import com.vominh.example.spring.mongo.data.repository.IUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {

    private final IUserRepo repo;

    public AppUserDetailService(IUserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userOpt = repo.findByEmail(email);
        if (userOpt.isPresent()) {
            return new AppUserDetails(userOpt.get());
        }

        throw new UsernameNotFoundException("User not found: " + email);
    }
}
