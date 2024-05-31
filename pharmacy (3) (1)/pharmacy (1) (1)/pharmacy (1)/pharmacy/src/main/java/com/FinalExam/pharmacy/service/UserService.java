package  com.FinalExam.pharmacy.service;

import  com.FinalExam.pharmacy.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.json.JSONException;
// import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserService extends UserDetailsService {

//    Boolean hasUserWithEmail(String email);
//
//    Boolean hasUserWithUsername(String username);
//    ResponseEntity<?> authenticate(String username, String password) throws JSONException;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<Users> getAllUsers();
    Users registerUser(String username, String email, String password);
    Users loginUser(String email, String password);
    Users getUserByEmail(String email);
    Users getUserById(Long adminId);
}
