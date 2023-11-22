package com.xjdl.framework.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringUtilsTest {
    @Test
    void use() {
        boolean b = StringUtils.hasText("");
        Assertions.assertFalse(b);
    }
}