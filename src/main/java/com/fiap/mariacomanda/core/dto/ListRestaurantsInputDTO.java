package com.fiap.mariacomanda.core.dto;

public class ListRestaurantsInputDTO {
    private int page;
    private int size;

    public ListRestaurantsInputDTO(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}