package com.xjdl.framework.context;

import com.xjdl.framework.beans.factory.Aware;

public interface ApplicationEventPublisherAware extends Aware {
	void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);
}
