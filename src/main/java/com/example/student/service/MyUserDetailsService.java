// package com.example.student.service;

// import com.example.student.entity.User;
// import com.example.student.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.*;
// import org.springframework.stereotype.Service;

// @Service
// public class MyUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         // 1. Tìm user trong DB
//         User user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + username));

//         // 2. Chuyển đổi User của mình thành User của Spring Security
//         return org.springframework.security.core.userdetails.User.builder()
//                 .username(user.getUsername())
//                 .password(user.getPassword())
//                 .authorities(user.getRole()) // Gán quyền (ví dụ ROLE_ADMIN)
//                 .build();
//     }
// }