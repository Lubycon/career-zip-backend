package com.careerzip.security.admin;

import com.careerzip.global.admin.entity.AdminAccount;
import com.careerzip.global.admin.repository.AdminAccountRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class FormLoginService implements UserDetailsService {

    private final AdminAccountRepository adminAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        AdminAccount adminAccount = adminAccountRepository.findByName(adminName)
                                                          .orElseThrow(AccountNotFoundException::new);
        return new User(adminAccount.getName(), adminAccount.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(adminAccount.getRole().getValue())));
    }
}
