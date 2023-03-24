package emt.emtau.service.implementations;

import org.springframework.stereotype.Service;
import emt.emtau.model.Category;
import emt.emtau.repository.impl.InMemoryCategoryRepository;
import emt.emtau.repository.jpa.CategoryRepository;
import emt.emtau.service.CategoryService;

import java.util.List;

//sega sme vo biznis sloj
@Service
public class CategoryServiceImpl implements CategoryService {

//    private final InMemoryCategoryRepository categoryRepository;

    private final CategoryRepository categoryRepository;

    //injecting dependencies, constructor-based tuka
    //vo ramkite na samiot konstruktor, koga ke se istancira samata
    //CategoryService implementacija, treba soodvetno i da se dodadat
    //zavisnosti do repozitorijata
//    public CategoryServiceImpl (InMemoryCategoryRepository categoryRepository){
//        this.categoryRepository = categoryRepository;
//    }

    public CategoryServiceImpl (CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name, String description) {
        if (name==null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        Category c = new Category(name, description);
        categoryRepository.save(c);
        return c;
    }

    @Override
    public Category update(String name, String description) {
        if (name==null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        Category c = new Category(name, description);
        categoryRepository.save(c);
        return c;
    }

    @Override
    public void delete(String name) {
        if (name==null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
//        categoryRepository.delete(name);
        categoryRepository.deleteByName(name);
    }

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategories(String searchText) {
//        return categoryRepository.search(searchText);
        return categoryRepository.findAllByNameLike(searchText);
    }
}
