package io.github.cursodsousa.libraryapi.validator;

import io.github.cursodsousa.libraryapi.exceptions.DuplicateRecordException;
import io.github.cursodsousa.libraryapi.model.Author;
import io.github.cursodsousa.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validate(Author author){
        if(existsAuthor(author)){
            throw new DuplicateRecordException("Autor já cadastrado!");
        }
    }

    private boolean existsAuthor(Author author){
        Optional<Author> authorFound = authorRepository.findByNameAndNationalityAndDateBirth(
                author.getName(),
                author.getNationality(),
                author.getDateBirth()
        );

        if(author.getId() == null){
            return authorFound.isPresent();
        }

        return !author.getId().equals(authorFound.get().getId()) && authorFound.isPresent();
    }
}
