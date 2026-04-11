package io.github.cursodsousa.libraryapi.controller.mappers;

import io.github.cursodsousa.libraryapi.controller.dto.BookCreateDTO;
import io.github.cursodsousa.libraryapi.model.Book;
import io.github.cursodsousa.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    public AuthorRepository authorRepository;

    @Mapping(target = "author", expression ="java(authorRepository.findById(dto.idAuthor()).orElse(null))")
    public abstract Book toEntity(BookCreateDTO dto);
}
