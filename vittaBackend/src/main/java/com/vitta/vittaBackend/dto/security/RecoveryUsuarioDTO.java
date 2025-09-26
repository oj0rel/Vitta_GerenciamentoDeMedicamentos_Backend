package com.vitta.vittaBackend.dto.security;

import com.vitta.vittaBackend.entity.Role;

import java.util.List;

public record RecoveryUsuarioDTO(
        Long id,
        String email,
        List<Role> roles
) {
}
