package com.xjdl.framework.context.support;

import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.InitializingBean;
import com.xjdl.framework.context.ApplicationContext;

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext implements BeanNameAware, InitializingBean {
	private boolean setIdCalled = false;
	private String[] configLocations;

	public AbstractRefreshableConfigApplicationContext(ApplicationContext parent) {
		super(parent);
	}

	@Override
	public void setBeanName(String name) {
		if (!this.setIdCalled) {
			super.setId(name);
			setDisplayName("ApplicationContext '" + name + "'");
		}
	}

	protected String[] getConfigLocations() {
		return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
	}

	/**
	 * 默认的配置文件，最好不要和配置的相同，默认是 Web 下的配置文件，如 /WEB-INF/applicationContext.xml
	 */
	protected String[] getDefaultConfigLocations() {
		return null;
	}

	@Override
	public void afterPropertiesSet() {
		if (!isActive()) {
			refresh();
		}
	}
}
