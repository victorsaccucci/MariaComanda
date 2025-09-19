package com.fiap.mariacomanda.core.dto.user;

import com.fiap.mariacomanda.core.domain.entity.UserType;

import java.util.UUID;

public class ListUserInputDTO {

    private UUID userId;
    private int page;
    private int size;

    public UUID getUserId() {
        return userId;
    }
    public int getPage() {
        return page;
    }
    public int getSize() {
        return size;
    }

    public ListUserInputDTO(UUID userId, int page, int size) {
        this.userId = userId;
        this.page = page;
        this.size = size;
    }
}
