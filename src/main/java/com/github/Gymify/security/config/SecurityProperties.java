package com.github.Gymify.security.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class SecurityProperties {

    static final String[] IGNORE_ANT_MATCHERS_POST = {
    };

    static final String[] IGNORE_ANT_MATCHERS_PUT = {
    };

    static final String[] IGNORE_ANT_MATCHERS_GET = {
            "/*",
    };
}
