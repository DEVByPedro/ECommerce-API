package com.example.e_commerce.user.account.clients.register.dto;

import com.example.e_commerce.user.account.clients.register.roles.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO( @Valid @NotNull String username,
                                                  @Valid @NotNull String fullname,
                                                  @Valid @NotNull String password,
                                                  @Valid @NotNull String userCpf,
                                                  @Valid @NotNull String email,
                                                  @Valid @NotNull UserRole role,
                                                  @Valid @NotNull String birthDate) {
}
