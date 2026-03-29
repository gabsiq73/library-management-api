package io.github.cursodsousa.libraryapi.controller;

import io.github.cursodsousa.libraryapi.controller.dto.BookCreateDTO;
import io.github.cursodsousa.libraryapi.controller.dto.ErrorResponse;
import io.github.cursodsousa.libraryapi.exceptions.DuplicateRecordException;
import io.github.cursodsousa.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private BookService bookService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookCreateDTO dto){
        try {
            // mapear dto para entidade
            // enviar entidade para o service validar e salvar na base
            // criar url para acesso dos dados do livro
            // retornar codigo created com header location

            return ResponseEntity.ok(dto);
        }catch (DuplicateRecordException e){
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

}
