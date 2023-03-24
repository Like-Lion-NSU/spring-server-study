package com.example.hana.Service;

import com.example.hana.Repository.UserRepository;;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

//@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements  UserDetailsService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
        public UserDetails loadUserByUsername(String username) {
        logger.info("[loadUserByUsername] loadUserByUsername 수행. username: {} ", username);
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
        return (UserDetails) userRepository.findByUserId(username).get();
    }
}
