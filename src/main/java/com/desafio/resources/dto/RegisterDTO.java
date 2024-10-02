package com.desafio.resources.dto;

import com.desafio.entities.enums.UserRole;

public record RegisterDTO(String login, String password, String name, String email, String cell, UserRole role) {
}
