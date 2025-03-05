package com.example.e_commerce.user.account.clients.login.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** Login DTO -> Used to verify informations,
 * if it exists, sends a verification code.
 */
public record LoginDTO(@Valid @NotNull String login,
                                           @Valid @NotNull String password) {
}
