package io.github.cursodsousa.libraryapi.controller.dto;

import io.github.cursodsousa.libraryapi.model.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookResponseDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publication_date,
        BookGenre bookGenre,
        BigDecimal price,
        AuthorDTO author
) {
}
