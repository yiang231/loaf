package com.xjdl.framework.aop.framework;

import com.xjdl.framework.aop.Advisor;

public interface Advised {
	Advisor[] getAdvisors();
	void removeAdvisor(int index) throws AopConfigException;
}
