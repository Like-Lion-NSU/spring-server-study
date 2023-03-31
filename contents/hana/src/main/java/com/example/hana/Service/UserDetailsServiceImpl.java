package com.example.hana.Service;

import com.example.hana.Dto.UserDetails;
import com.example.hana.Repository.UserRepository;;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

//    @Autowired
//    public UserDetailsServiceImpl(UserRepository userRepository){
//        this.userRepository=userRepository;
//    }

    @Override
        public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
        logger.info("[loadUserByUsername] loadUserByUsername 수행. username: {} ", username);

        return userRepository.findByUserId(username).orElse(null);
    }
}
