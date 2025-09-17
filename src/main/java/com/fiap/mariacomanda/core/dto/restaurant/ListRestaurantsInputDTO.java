package com.fiap.mariacomanda.core.dto.restaurant;

import lombok.Getter;

@Getter
public class ListRestaurantsInputDTO {
    private int page;
    private int size;

    public ListRestaurantsInputDTO(int page, int size) {
        this.page = page;
        this.size = size;
    }

}