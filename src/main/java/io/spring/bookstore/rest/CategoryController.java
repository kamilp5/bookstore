package io.spring.bookstore.rest;

import io.spring.bookstore.model.Category;
import io.spring.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category addCategory(@Valid @RequestBody Category category){
        return categoryRepository.save(category);
    }

}
