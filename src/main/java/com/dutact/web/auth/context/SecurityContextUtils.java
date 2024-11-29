package com.dutact.web.auth.context;

import com.dutact.web.auth.UserPrincipal;
import com.dutact.web.auth.factors.Role;
import jakarta.annotation.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityContextUtils {

    @SuppressWarnings("unchecked")
    public static boolean hasRole(Role role) {
        Collection<SimpleGrantedAuthority> authorities =
                (Collection<SimpleGrantedAuthority>) getAuthentication()
                        .getAuthorities();

        return authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role.name()));
    }

    public static String getUsername() {
        return ((UserPrincipal) getAuthentication().getPrincipal()).getUsername();
    }

    public static boolean hasAuthentication() {
        return getAuthentication() != null;
    }

    @Nullable
    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
