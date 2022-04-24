package com.github.Gymify.core.util;

import com.github.Gymify.configuration.GLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectUtils implements GLogger {

    public static void copyProperties(Object o1, Object o2) {
        try {
            BeanUtils.copyProperties(o1, o2);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
