package com.spring.guitarsite.dto;

import com.spring.guitarsite.model.Guitar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuitarDto {
    private Long id;
    private String name;
    private String imageUrl;
    private String typeDescription;

    public static GuitarDto fromGuitar(Guitar guitar) {
        return new GuitarDto(
                guitar.getId(),
                guitar.getName(),
                guitar.getImageUrl(),
                guitar.getType().getDescription()
        );
    }
}
