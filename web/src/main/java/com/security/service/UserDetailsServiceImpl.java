package com.security.service;

import com.taxi.service.interfaces.user_module.IUserService;
import io.github.frame_code.domain.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@CommonsLog
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = service.findByEmail(username);
        if(userOpt.isEmpty())
            return null;

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(buildRole(userOpt.get()));
        authorities.addAll(buildPermission(userOpt.get()));

        return new org.springframework.security.core.userdetails
                .User(userOpt.get().getFullNames(), userOpt.get().getPasswordHash(), authorities);
    }

    private SimpleGrantedAuthority buildRole(User user) {
        String authority = "ROLE_" + user.getRole().getRoleName().name();
        return new SimpleGrantedAuthority(authority);
    }

    private List<SimpleGrantedAuthority> buildPermission(User user) {
        return user.getRole()
                .getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName().name()))
                .toList();
    }


}
