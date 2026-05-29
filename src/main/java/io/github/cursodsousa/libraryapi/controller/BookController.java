package io.github.cursodsousa.libraryapi.controller;

import io.github.cursodsousa.libraryapi.controller.dto.BookCreateDTO;
import io.github.cursodsousa.libraryapi.controller.dto.BookResponseDTO;
import io.github.cursodsousa.libraryapi.controller.dto.ErrorResponse;
import io.github.cursodsousa.libraryapi.controller.mappers.BookMapper;
import io.github.cursodsousa.libraryapi.exceptions.DuplicateRecordException;
import io.github.cursodsousa.libraryapi.model.Book;
import io.github.cursodsousa.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid BookCreateDTO dto) {
        Book book = bookMapper.toEntity(dto);
        bookService.save(book);
        URI location = generateHeaderLocation(book.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookDetails(@PathVariable UUID id){
        return bookService.findById(id)
                .map(book -> {
                    var dto = bookMapper.toDTO(book);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable UUID id){
        return bookService.findById(id)
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
