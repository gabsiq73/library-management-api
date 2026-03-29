package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.model.Author;
import io.github.cursodsousa.libraryapi.model.BookGenre;
import io.github.cursodsousa.libraryapi.model.Book;
import io.github.cursodsousa.libraryapi.repository.AuthorRepository;
import io.github.cursodsousa.libraryapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = bookRepository
                .findById(UUID.fromString("7ada2764-400f-4fd9-8658-4c21941f33a9"))
                .orElse(null);
        livro.setPublication_date(LocalDate.of(2016, 6,1));
    }

    @Transactional
    public void executar(){

        // Salva Author
        Author author = new Author();
        author.setName("Teste Francisco");
        author.setNationality("Brasileira");
        author.setDateBirth(LocalDate.of(1953, 2, 22));

        authorRepository.saveAndFlush(author);

        //Salva Livro
        Book book = new Book();
        book.setIsbn("198494664");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenre.CIENCIA);
        book.setTitle("Teste Livro da Francisco");
        book.setPublication_date(LocalDate.of(1980, 1, 2));

        book.setAuthor(author);

        bookRepository.saveAndFlush(book);

        if(author.getName().equals("Teste Francisco")){
            throw new RuntimeException("Rollback");
        }
    }
}
