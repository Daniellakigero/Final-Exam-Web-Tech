package  com.FinalExam.pharmacy.repo;

import  com.FinalExam.pharmacy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can add custom query methods if needed
}
