package com.xjdl.framework.context.support;

import com.xjdl.framework.beans.BeansException;
import com.xjdl.framework.beans.factory.config.ConfigurableListableBeanFactory;
import com.xjdl.framework.beans.factory.support.DefaultListableBeanFactory;
import com.xjdl.framework.context.ApplicationContext;
import com.xjdl.framework.context.ApplicationContextException;

import java.io.IOException;

/**
 * DefaultListableBeanFactory 关联的起始点
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
	private volatile DefaultListableBeanFactory beanFactory;

	public AbstractRefreshableApplicationContext(ApplicationContext parent) {
		super(parent);
	}

	@Override
	public final ConfigurableListableBeanFactory getBeanFactory() {
		DefaultListableBeanFactory beanFactory = this.beanFactory;
		if (beanFactory == null) {
			throw new IllegalStateException("BeanFactory not initialized or already closed - " +
					"call 'refresh' before accessing beans via the ApplicationContext");
		}
		return beanFactory;
	}

	@Override
	protected void cancelRefresh(BeansException ex) {
		DefaultListableBeanFactory beanFactory = this.beanFactory;
		if (beanFactory != null) {
			beanFactory.setSerializationId(null);
		}
		super.cancelRefresh(ex);
	}

	protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException;

	@Override
	protected final void refreshBeanFactory() throws BeansException {
		if (hasBeanFactory()) {
			closeBeanFactory();
		}
		try {
			DefaultListableBeanFactory beanFactory = createBeanFactory();
			beanFactory.setSerializationId(getId());
			loadBeanDefinitions(beanFactory);
			this.beanFactory = beanFactory;
		} catch (IOException ex) {
			throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);
		}
	}


	protected final boolean hasBeanFactory() {
		return (this.beanFactory != null);
	}

	protected DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory(getInternalParentBeanFactory());
	}

	@Override
	protected final void closeBeanFactory() {
		DefaultListableBeanFactory beanFactory = this.beanFactory;
		if (beanFactory != null) {
			beanFactory.setSerializationId(null);
			this.beanFactory = null;
		}
	}
}
