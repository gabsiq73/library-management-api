package io.github.cursodsousa.libraryapi.controller.mappers;

import io.github.cursodsousa.libraryapi.controller.dto.AuthorDTO;
import io.github.cursodsousa.libraryapi.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorDTO dto);
    AuthorDTO toDTO(Author author);
}
