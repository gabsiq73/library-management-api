package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.exceptions.OperationNotAllowedException;
import io.github.cursodsousa.libraryapi.model.Author;
import io.github.cursodsousa.libraryapi.repository.AuthorRepository;
import io.github.cursodsousa.libraryapi.repository.BookRepository;
import io.github.cursodsousa.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorValidator validator;

    public Author save(Author author){
        validator.validate(author);
        return authorRepository.save(author);
    }

    public void update(Author author){
        if(author.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessário que o autor já esteja salvo na base.");
        }
        validator.validate(author);
        authorRepository.save(author);
    }

    public Optional<Author> findById(UUID id){
        return authorRepository.findById(id);
    }

    public void delete(Author author) {
        if(haveABook(author)){
            throw new OperationNotAllowedException("Não é permitido excluir um autor que possiu livros cadastrados!");
        }
        authorRepository.delete(author);
    }

    public List<Author> search(String name, String nationality){
        if(name != null && nationality != null){
            return authorRepository.findByNameAndNationality(name, nationality);
        }

        if(name != null){
            return authorRepository.findByName(name);
        }

        if(nationality != null){
            return authorRepository.findByNationality(nationality);
        }

        return authorRepository.findAll();
    }

    public List<Author> searchByExample(String name, String nationality){
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author, matcher);

        return authorRepository.findAll(authorExample);

    }

    public boolean haveABook(Author author){
        return bookRepository.existsByAuthor(author);
    }
}
