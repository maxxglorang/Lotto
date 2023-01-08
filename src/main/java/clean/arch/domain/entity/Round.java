package clean.arch.domain.entity;

import java.time.LocalDate;

public record Round(
        String name,
        LocalDate drawnAt
) {
}