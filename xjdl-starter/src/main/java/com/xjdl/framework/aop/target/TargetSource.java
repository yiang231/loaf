package com.xjdl.framework.aop.target;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TargetSource {
    private final Object target;

    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }

    @Override
    public String toString() {
        return "TargetSource: " +
                (this.target != null ? "target class [" + this.target.getClass().getName() + "]" : "no target class");
    }
}
