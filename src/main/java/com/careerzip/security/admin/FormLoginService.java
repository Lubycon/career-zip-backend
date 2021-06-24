package com.careerzip.security.admin;

import com.careerzip.domain.account.entity.Role;
import com.careerzip.global.admin.entity.AdminAccount;
import com.careerzip.global.admin.repository.AdminAccountRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class FormLoginService implements UserDetailsService {

    private final AdminAccountRepository adminAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        log.info(">>>>>>>>>>>>>>>>> Come>>>>>>>>>>>>>");
        log.info(">>>>>>>>>>>>>>>>>>>>> {}", adminName);
        AdminAccount adminAccount = adminAccountRepository.findByName(adminName)
                                                          .orElseThrow(AccountNotFoundException::new);
        return new User(adminAccount.getName(), adminAccount.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(adminAccount.getRole().getValue())));
    }
}
