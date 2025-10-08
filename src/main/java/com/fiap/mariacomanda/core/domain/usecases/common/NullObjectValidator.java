package com.fiap.mariacomanda.core.domain.usecases.common;

/**
 * Validador para objetos nulos.
 * Responsável por validar se objetos obrigatórios não são nulos.
 * Segue o princípio da responsabilidade única.
 */
public class NullObjectValidator {

    /**
     * Valida se o objeto não é null.
     *
     * @param object Objeto a ser validado
     * @param objectName Nome do objeto para mensagem de erro
     * @throws IllegalArgumentException se o objeto for null
     */
    public void validateNotNull(Object object, String objectName) {
        if (object == null) {
            throw new IllegalArgumentException(objectName + " cannot be null");
        }
    }

    /**
     * Valida se a string não é null e não está vazia.
     *
     * @param value String a ser validada
     * @param fieldName Nome do campo para mensagem de erro
     * @throws IllegalArgumentException se a string for null ou vazia
     */
    public void validateNotNullOrEmpty(String value, String fieldName) {
        validateNotNull(value, fieldName);
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }
}