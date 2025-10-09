package com.fiap.mariacomanda.core.domain.usecases.usertype;

import java.util.UUID;

public interface DeleteUserTypeUseCase {
    void execute(UUID id, UUID requesterUserId);
}
