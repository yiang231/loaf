package com.xjdl.app.event;

import com.xjdl.framework.context.ApplicationListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomEventListener0 implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        log.debug("{}", "CustomEventListener0.onApplicationEvent");
    }
}