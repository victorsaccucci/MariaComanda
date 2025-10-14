package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantValidatorTest {

    @Test
    void shouldThrowWhenRestaurantIsNull() {
        UUID ownerId = UUID.randomUUID();

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> RestaurantValidator.validateUserOwnsRestaurant(null, ownerId)
        );

        assertEquals("Restaurant cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowWhenOwnerUserIdIsNull() {
        Restaurant restaurant = new Restaurant();

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> RestaurantValidator.validateUserOwnsRestaurant(restaurant, null)
        );

        assertEquals("Owner user ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenRestaurantIdIsNull() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> RestaurantValidator.validateRestaurantId(null)
        );

        assertEquals("Restaurant ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldNotThrowWhenRestaurantIdIsValid() {
        UUID validId = UUID.randomUUID();

        assertDoesNotThrow(() -> RestaurantValidator.validateRestaurantId(validId));
    }

    @Test
    void constructorShouldThrowExceptionWhenInvoked() throws Exception {
        Constructor<RestaurantValidator> constructor = RestaurantValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertEquals(null, exception.getMessage());
    }
}