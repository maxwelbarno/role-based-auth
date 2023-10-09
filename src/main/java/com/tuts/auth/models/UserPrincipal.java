// package com.tuts.auth.models;

// import java.util.Collection;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import lombok.AllArgsConstructor;
// import lombok.NoArgsConstructor;

// @AllArgsConstructor
// @NoArgsConstructor
// public class UserPrincipal implements UserDetails {
//     @Autowired
//     private User user;

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         List<GrantedAuthority> authorities = user.getRoles().stream()
//                 .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//         return authorities;

//     }

//     @Override
//     public String getPassword() {
//         return user.getPassword();
//     }

//     @Override
//     public String getUsername() {
//         return user.getUsername();
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }
// }
