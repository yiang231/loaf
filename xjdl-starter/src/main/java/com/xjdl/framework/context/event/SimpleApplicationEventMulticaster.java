package com.xjdl.framework.context.event;

import com.xjdl.framework.beans.factory.BeanFactory;
import com.xjdl.framework.context.ApplicationEvent;
import com.xjdl.framework.context.ApplicationListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
	public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
		setBeanFactory(beanFactory);
	}

	public SimpleApplicationEventMulticaster() {
	}

	@Override
	public void multicastEvent(ApplicationEvent event) {
		for (ApplicationListener<?> applicationListener : getApplicationListeners(event)) {
			invokeListener(applicationListener, event);
		}
	}

	protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
		doInvokeListener(listener, event);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
		try {
			listener.onApplicationEvent(event);
		} catch (ClassCastException ex) {
			String msg = ex.getMessage();
			if (msg == null || matchesClassCastMessage(msg, event.getClass())) {
				if (log.isTraceEnabled()) {
					log.trace("Non-matching event type for listener: " + listener, ex);
				}
			} else {
				throw ex;
			}
		}
	}

	private boolean matchesClassCastMessage(String classCastMessage, Class<?> eventClass) {
		// On Java 8, the message starts with the class name: "java.lang.String cannot be cast..."
		if (classCastMessage.startsWith(eventClass.getName())) {
			return true;
		}
		// On Java 11, the message starts with "class ..." a.k.a. Class.toString()
		if (classCastMessage.startsWith(eventClass.toString())) {
			return true;
		}
		// On Java 9, the message used to contain the module name: "java.base/java.lang.String cannot be cast..."
		int moduleSeparatorIndex = classCastMessage.indexOf('/');

		// Assuming an unrelated class cast failure...
		return moduleSeparatorIndex != -1 && classCastMessage.startsWith(eventClass.getName(), moduleSeparatorIndex + 1);
	}
}
