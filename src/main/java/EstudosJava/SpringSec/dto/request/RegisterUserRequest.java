package EstudosJava.SpringSec.dto.request;

import jakarta.validation.constraints.NotEmpty;

import EstudosJava.SpringSec.entity.Role;

public record RegisterUserRequest(@NotEmpty(message = "Nome é obrigatorio") String name,
                                  @NotEmpty(message = "E-mail é obrigatório") String email,
                                  @NotEmpty(message = "Senha é obrigatoria") String password,
                                  Role role) {

}
