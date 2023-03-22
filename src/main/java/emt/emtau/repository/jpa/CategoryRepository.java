package webp.testau.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webp.testau.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query(value = "select * from ")

    List<Category> findAllByNameLike(String text);

    void deleteByName(String name);
}
