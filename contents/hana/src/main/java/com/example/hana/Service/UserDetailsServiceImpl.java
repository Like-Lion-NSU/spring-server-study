package com.example.hana.Service;

import com.example.hana.Config.JwtTokenProvider;
import com.example.hana.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements  UserDetailsService {

    //private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;


    @Override
        public UserDetails loadUserByUsername(String username) {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. username: {} "+ username);
        return (UserDetails) userRepository.findByUserId(username).get();
    }
}
