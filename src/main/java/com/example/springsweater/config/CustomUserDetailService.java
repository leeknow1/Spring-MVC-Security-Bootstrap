package com.example.springsweater.config;


import com.example.springsweater.entity.UserEntity;
import com.example.springsweater.repository.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    public CustomUserDetailService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findByLogin(username);

        if(user==null){
            throw new UsernameNotFoundException("User not found!");
        }

        return new CustomUserDetail(user);
    }
}