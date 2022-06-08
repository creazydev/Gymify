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
    void test_valid_pass() {
        assertTrue(passwordValidator.test("test1234"));
    }

    @Test
    void test_null_failed() {
        assertFalse(passwordValidator.test(null));
    }

    @Test
    void test_blank_failed() {
        assertFalse(passwordValidator.test(""));
    }

    @Test
    void test_7CharsTooShort_failed() {
        assertFalse(passwordValidator.test("aaaaaaa"));
    }

    @Test
    void test_256CharsTooLong_failed() {
        assertFalse(passwordValidator.test(str.substring(0, 256)));
    }
}
