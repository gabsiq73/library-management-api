package io.github.cursodsousa.libraryapi.controller.dto;

import io.github.cursodsousa.libraryapi.model.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookCreateDTO(
        @ISBN
        @NotBlank(message = "campo obrigatório")
        String isbn,
        @NotBlank(message = "campo obrigatório")
        String title,
        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        LocalDate publication_date,
        BookGenre bookGenre,
        BigDecimal price,
        @NotNull(message = "campo obrigatório")
        UUID idAuthor
        ) {
}
