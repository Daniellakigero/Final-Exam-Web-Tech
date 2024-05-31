package com.FinalExam.pharmacy.controller.admin;

import com.FinalExam.pharmacy.model.Category;
import com.FinalExam.pharmacy.model.UserRole;
import com.FinalExam.pharmacy.model.Users;
import com.FinalExam.pharmacy.service.CategoryService;
import com.FinalExam.pharmacy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {

        Long adminId = category.getUser().getId();

        logger.info("Admin ID in session: {}", adminId);

        if (adminId == null) {
            // Handle the case where the admin ID is not found in the session
            logger.error("Admin ID not found in session");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String categoryName = category.getName();
        String categoryDescription = category.getDescription();

        // Log the retrieved values
        logger.info("Admin ID: {}", adminId);
        logger.info("Category Name: {}", categoryName);
        logger.info("Category Description: {}", categoryDescription);

        Users admin = userService.getUserById(adminId);

        if (admin != null && admin.getRole() == UserRole.ADMIN) {

            Category createdCategory = categoryService.createCategory(category);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        logger.info("Updating category with ID: {}", id);

        Long adminId = updatedCategory.getUser().getId();
        logger.info("Admin ID in session: {}", adminId);

        if (adminId == null) {
            // Handle the case where the admin ID is not found in the session
            logger.error("Admin ID not found in session");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Category existingCategory = categoryService.getCategoryById(id);

        if (existingCategory != null) {

            existingCategory.setName(updatedCategory.getName());
            existingCategory.setDescription(updatedCategory.getDescription());

            Users admin = userService.getUserById(adminId);

            if (admin != null && admin.getRole() == UserRole.ADMIN) {

                Category savedCategory = categoryService.updateCategory(id, existingCategory);

                if (savedCategory != null) {
                    logger.info("Category updated successfully with ID: {}", id);
                    return ResponseEntity.ok(savedCategory);
                } else {
                    logger.error("Failed to update category with ID: {}", id);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

        } else {
            logger.warn("Category with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Deleting category with ID: {}", id);
        Category category = categoryService.getCategoryById(id);

        if (category != null) {

            Long adminId = category.getUser().getId();

            logger.info("Admin ID in session: {}", adminId);

            if (adminId == null) {
                // Handle the case where the admin ID is not found in the session
                logger.error("Admin ID not found in session");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Users admin = userService.getUserById(adminId);

            if (admin != null && admin.getRole() == UserRole.ADMIN) {

                categoryService.deleteCategory(id);
                logger.info("Category deleted successfully with ID: {}", id);
                return ResponseEntity.noContent().build();

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            logger.warn("Category with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
