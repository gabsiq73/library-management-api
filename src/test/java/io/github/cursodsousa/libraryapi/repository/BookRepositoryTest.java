package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Author;
import io.github.cursodsousa.libraryapi.model.BookGenre;
import io.github.cursodsousa.libraryapi.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    AuthorRepository author_repository;

    @Autowired
    BookRepository repository;

    @Test
    void salvarTest(){
      Book book = new Book();
      book.setIsbn("198494664");
      book.setPrice(BigDecimal.valueOf(150));
      book.setGenre(BookGenre.CIENCIA);
      book.setTitle("Primeiro Livro");
      book.setPublication_date(LocalDate.of(1980, 1, 2));

      Author author = author_repository.findById(UUID
              .fromString("225b2d68-089c-41eb-8c2a-450aa5fc1a06"))
              .orElse(null);
      book.setAuthor(author);

      repository.save(book);
    }

    @Test
    void salvarCascadeTest(){
        Book book = new Book();
        book.setIsbn("198494664");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGenre(BookGenre.FICCAO);
        book.setTitle("Outro Livro");
        book.setPublication_date(LocalDate.of(1980, 1, 2));

        Author author = new Author();
        author.setName("João");
        author.setNationality("Brasileira");
        author.setDateBirth(LocalDate.of(1953, 2, 22));

        book.setAuthor(author);

        repository.save(book);
    }

    @Test
    void atualizarAuthorDoLivro(){
        UUID id = UUID.fromString("be99aef2-2b98-4644-9f60-848a1b1c0a85");
        var livroParaAtualalizar = repository.findById(id).orElse(null);

        UUID idAuthor = UUID.fromString("79e3ac53-dc54-4cad-9ce5-0b624ebcc281");
        Author author = author_repository.findById(idAuthor).orElse(null);

        livroParaAtualalizar.setAuthor(author);

        repository.save(livroParaAtualalizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("be99aef2-2b98-4644-9f60-848a1b1c0a85");
        repository.deleteById(id);
    }

    @Test
    void deletarCascade(){
        UUID id = UUID.fromString("674b2957-2a7a-4e90-830f-ca8c03ff1d0f");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("ce9a0abc-e83c-4986-9676-3ce6b29e7e25");
        Book book = repository.findById(id).orElse(null);
        System.out.print("Livro: ");
        System.out.println(book.getTitle());
        System.out.print("Author: ");
        System.out.println(book.getAuthor().getName());

    }

    @Test
    void pesquisaPorTituloTest(){
        List<Book> lista = repository.findByTitle("O roubo da casa livre");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorISBNTest(){
        List<Book> lista = repository.findByIsbn("2085-6454");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPreco(){
        List<Book> lista = repository.findByTitleAndPrice("Primeiro Livro", BigDecimal.valueOf(150.00));
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.findAllOrderedByTitleAndPrice();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAuthoresDosLivrosComQueryJPQL(){
        var resultado = repository.findAllAuthorsByBooks();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitlesComQueryJPQL(){
        var resultado = repository.findDistinctBookTitles();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLivroPorGeneroQueryParam(){
        var resultado = repository.findByGenrePositional(BookGenre.MISTERIO, "price");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGenero(){
        repository.deleteByGenre(BookGenre.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTest(){
        repository.updatePublicationDate(LocalDate.of(2000,01,25));
    }
}