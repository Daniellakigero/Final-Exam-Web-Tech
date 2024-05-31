package  com.FinalExam.pharmacy.repo;

import  com.FinalExam.pharmacy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom query methods if needed
}
