package com.fiap.mariacomanda.core.domain.usecases.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;

import java.util.List;

public interface ListUserTypeUseCase {
    List<UserType> execute(int page, int size);
}
