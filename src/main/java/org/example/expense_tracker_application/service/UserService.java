package org.example.expense_tracker_application.service;

import org.example.expense_tracker_application.model.User;
import org.example.expense_tracker_application.model.VerificationToken;
import org.example.expense_tracker_application.repository.UserRepository;
import org.example.expense_tracker_application.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    //We implement the UserDetailsService to allow Spring Security to interact with custom data sources where user information is stored This could be a database.
    // Also for custom authentication logic (we havecontrol over how user inormation is retrieved)
    // For security configuration (it is crucial for securing web applications as it provides the necessary user data that Spring Security needs to authenticate and authorize users.)

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;


    @Transactional
    public boolean saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<String>roles = new HashSet<>();
        roles.add("USER"); // here a role "user" has been added to the set of roles
        user.setRoles(roles);
        try {
            userRepository.save(user);
            sendVerificationEmail(user);

        } catch (MailException e) {
            //log in exception (optional)
            System.out.println("Fail to send Verification Email:" + e.getMessage());
            return false;
        }
        return true;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
        } else {
            System.err.println("User not found with ID: " + id);
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by username
        User user = findByUserName(username);
        // If user is not found
        if (user == null) {
            // Throw UsernameNotFoundException
            throw new UsernameNotFoundException("User not found");
        } /*  if (!user.isVerified()) {
            throw new UsernameNotFoundException("User email is not verified!");
        }
        .accountLocked(!user.isVerified()) is same like the above if method*/
        // Build and return UserDetails object with user details and authorities
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername()) // Set the username
                .password(user.getPassword()) // Set the password
                .authorities(user.getRoles().toArray(new String[0])) // Set authorities/roles
                .accountLocked(!user.isVerified()) // if the account is verified, then this statement is false so account won't be locked if account isn't verified, then statement is true and account will be suspended
                .build();
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username); // Calls the User repository method to find the user by username
    }

    public  User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        tokenRepository.save(verificationToken);

    }

    public VerificationToken getVerificationToken (String token){
        return tokenRepository.findByToken(token);
    }

    public void verifyUser (String token){
        VerificationToken verificationToken = getVerificationToken(token);
        if (
                verificationToken != null // does exist
                        &&
                        verificationToken.getExpiryDate().isAfter(LocalDateTime.now())  // not expired
        ) {
            User user = verificationToken.getUser();
            user.setVerified(true);
            userRepository.save(user);
            //tokenRepository.delete(verificationToken); we might keep it for our future logs
        }
    }

    private void sendVerificationEmail (User user) throws MailException {
        String token = UUID.randomUUID().toString();
        createVerificationToken(user,token);
        String recipientAddress = user.getEmail();
        String subject = "Email Verification";
        String confirmationUrl = "http://localhost:7272/verify?token=" + token;
        String message = "Please click the link below to verify your email address:\n" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }


}
