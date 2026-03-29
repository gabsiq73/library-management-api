package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Author;
import io.github.cursodsousa.libraryapi.model.BookGenre;
import io.github.cursodsousa.libraryapi.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void salvarTest(){
        Author author = new Author();
        author.setName("José");
        author.setNationality("Brasileira");
        author.setDateBirth(LocalDate.of(1953, 2, 22));

        var authorSalvo = repository.save(author);
        System.out.println("Author Salvo: " + authorSalvo);
    }

    @Test
    public void atualizarTeste(){
        var id = UUID.fromString("6d013de4-05de-4ca2-bcfb-eda65b9bed22");
        Optional<Author> possivelauthor = repository.findById(id);

        if(possivelauthor.isPresent()){
            Author authorEncontrado = possivelauthor.get();
            System.out.println("Dados do author: ");
            System.out.println(authorEncontrado);

            authorEncontrado.setDateBirth(LocalDate.of(1960, 5, 16));

            repository.save(authorEncontrado);

        }
    }

    @Test
    public void listarTest(){
       List<Author> list = repository.findAll();
       list.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de authores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("aa6118a3-0e77-4b2b-be4b-15bd3fdb4588");
        repository.deleteById(id);
    }

    @Test
    public void deletePorObjeto(){
        var id = UUID.fromString("6d013de4-05de-4ca2-bcfb-eda65b9bed22");
        var pessoa = repository.findById(id).get();
        repository.delete(pessoa);
    }

    @Test
    public void salvarAuthorComLivrosTest(){
        Author author = new Author();
        author.setName("Antonio");
        author.setNationality("Americano");
        author.setDateBirth(LocalDate.of(1983, 5, 27));

        Book book = new Book();
        book.setIsbn("2085-6454");
        book.setPrice(BigDecimal.valueOf(204));
        book.setGenre(BookGenre.MISTERIO);
        book.setTitle("O roubo da casa assombrada");
        book.setPublication_date(LocalDate.of(1999, 9, 15));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setIsbn("2085-6454");
        book2.setPrice(BigDecimal.valueOf(350));
        book2.setGenre(BookGenre.MISTERIO);
        book2.setTitle("O roubo da casa livre");
        book2.setPublication_date(LocalDate.of(2000, 4, 15));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        repository.save(author);
        bookRepository.saveAll(author.getBooks());

    }

    @Test
    public void pegarLivrosAuthorTest(){
        var id = UUID.fromString("d099a655-fa73-41f9-b764-aef49a841d78");
        var author = repository.findById(id).orElse(null);

        //Buscar livros do author
        List<Book> livrosLista = bookRepository.findByAuthor(author);
        author.setBooks(livrosLista);

        author.getBooks().forEach(System.out::println);
    }
}
