package com.github.Gymify.user.util;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
import java.util.function.Predicate;

public class PasswordValidator implements Predicate<String> {
    private final static int MIN = 8;
    private final static int MAX = 255;

    @Override
    public boolean test(String s) {
        return Objects.nonNull(s)
                && Strings.isNotBlank(s)
                && s.length() >= MIN
                && s.length() <= MAX;
    }
}
