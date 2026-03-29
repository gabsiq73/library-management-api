package io.github.cursodsousa.libraryapi.controller.dto;

import io.github.cursodsousa.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Campo Obrigatório")
        @Size(min = 2, max = 100, message = "Campo fora do tamanho padrão!")
        String name,
        @NotNull(message = "Campo Obrigatório")
        @Past(message = "Não pode ser uma data futura!")
        LocalDate dateBirth,
        @NotBlank(message = "Campo Obrigatório")
        @Size(min = 2, max = 50, message = "Campo fora do tamanho padrão!")
        String nationality) {

}
