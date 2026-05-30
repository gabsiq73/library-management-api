package io.github.cursodsousa.libraryapi.repository.specs;

import io.github.cursodsousa.libraryapi.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn){
        return ((root, query, cb) -> cb.equal(root.get("isbn"), isbn));
    }

    public static Specification<Book> genreEqual(String genre){
        return ((root, query, cb) -> cb.equal(root.get("genre"), genre));
    }

    public static Specification<Book> titleLike(String title){
        return ((root, query, cb) ->
                cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%"));
    }

//    public static Specification<Book> nameAuthorLike(String nameAuthor){
//        return ((root, query, cb) -> cb.like(root.get()));
//    }
    
}
