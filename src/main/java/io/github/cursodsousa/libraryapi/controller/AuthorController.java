package io.github.cursodsousa.libraryapi.controller;

import io.github.cursodsousa.libraryapi.controller.dto.AuthorDTO;
import io.github.cursodsousa.libraryapi.controller.dto.ErrorResponse;
import io.github.cursodsousa.libraryapi.controller.mappers.AuthorMapper;
import io.github.cursodsousa.libraryapi.exceptions.OperationNotAllowedException;
import io.github.cursodsousa.libraryapi.exceptions.DuplicateRecordException;
import io.github.cursodsousa.libraryapi.model.Author;
import io.github.cursodsousa.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid AuthorDTO dto){
        try {

            var authorEntity = mapper.toEntity(dto);
            authorService.save(authorEntity);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorEntity.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicateRecordException e){
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> findByAuthor(@PathVariable("id") String id){
        var idAuthor= UUID.fromString(id);

        return authorService
                .findById(idAuthor)
                .map(author -> {
                    AuthorDTO dto = mapper.toDTO(author);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id){

        try {
            var idAuthor= UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(idAuthor);

            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            authorService.delete(authorOptional.get());

            return ResponseEntity.noContent().build();
        } catch (OperationNotAllowedException e){
            var errorDTO = ErrorResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality){
        List<Author> resultado = authorService.searchByExample(name, nationality);
        List<AuthorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id,
            @RequestBody @Valid AuthorDTO dto){
        try {
            var idAuthor= UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(idAuthor);

            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var author= authorOptional.get();
            author.setName(dto.name());
            author.setNationality(dto.nationality());
            author.setDateBirth(dto.dateBirth());

            authorService.update(author);

            return ResponseEntity.noContent().build();
        }catch (DuplicateRecordException e){
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
