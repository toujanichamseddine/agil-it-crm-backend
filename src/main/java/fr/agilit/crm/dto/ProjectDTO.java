package fr.agilit.crm.dto;

import java.time.LocalDateTime;

public record ProjectDTO(
        long id,
        String title,
        String description
) {
}
