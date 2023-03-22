package webp.testau.repository.impl;

import org.springframework.stereotype.Repository;
import webp.testau.bootstrap.DataHolder;
import webp.testau.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//kazuvame deka ova e nesto sto zema podatoci, repository e
@Repository
public class InMemoryCategoryRepository {

    //listanje na elementite
    public List<Category> findAll(){
        return DataHolder.categories;
    }

    //zacuvuvanje na element
    public Category save(Category c) {
        if (c==null || c.getName()==null || c.getName().isEmpty()){
            return null;
        }
        //ako element r ima isto ime so noviot c, ke se prebrise
        DataHolder.categories.removeIf(r->r.getName().equals(c.getName()));
        //ako nema pa nisto samo dodavame
        DataHolder.categories.add(c);
        return c;
    }

    //prebaruvanje
    public Optional<Category> findByName(String name){
        //najdi go prviot element koj go ima imeto sto go barame
        return DataHolder.categories.stream().filter(r->r.getName().equals(name)).findFirst();
    }

    public Optional<Category> findById(Long id){
        //najdi go prviot element koj go ima imeto sto go barame
        return DataHolder.categories.stream().filter(r->r.getId().equals(id)).findFirst();
    }

    //prebaruvanje da se vrati lista od kategorii spored tekst
    public List<Category> search(String text){
        return DataHolder.categories.stream().filter(r->r.getName().contains(text)
        || r.getDescription().contains(text)).collect(Collectors.toList());
        //collect za da gi vrati kako kolekcija t.e. lista
    }

    //brisenje
    public void delete (String name){
        if (name==null) {
            return;
        }
        DataHolder.categories.removeIf(r->r.getName().equals(name));
    }
}
