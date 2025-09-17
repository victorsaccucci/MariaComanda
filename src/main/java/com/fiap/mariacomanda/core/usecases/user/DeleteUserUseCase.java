package com.fiap.mariacomanda.core.usecases.user;

import java.util.UUID;

public interface DeleteUserUseCase {
    void execute(UUID id);
}
