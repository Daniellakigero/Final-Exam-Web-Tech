package  com.FinalExam.pharmacy.repo;

import  com.FinalExam.pharmacy.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users>  findByUsername (String username);

    Optional<Users>  findByEmail (String email);

    Optional<Users> findFirstByEmail(String email);

    Optional<Users> findFirstByUsername(String username);
}