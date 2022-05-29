package com.github.Gymify.user.util;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {
    private final PasswordValidator passwordValidator = new PasswordValidator();
    private final String str = IntStream
        .rangeClosed(0, 255)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining());

    @Test
    void shouldPassWhenMeetConditions() {
        assertTrue(passwordValidator.test("test1234"));
        assertTrue(passwordValidator.test(str.substring(0, 255)));
    }

    @Test
    void shouldFailWhenNull() {
        assertFalse(passwordValidator.test(null));
    }

    @Test
    void shouldFailWhenBlank() {
        assertFalse(passwordValidator.test(""));
    }

    @Test
    void shouldFailWhenTooShort() {
        assertFalse(passwordValidator.test("aaaaaaa"));
    }

    @Test
    void shouldFailWhenTooLong() {
        assertFalse(passwordValidator.test(str.substring(0, 256)));
    }
}
