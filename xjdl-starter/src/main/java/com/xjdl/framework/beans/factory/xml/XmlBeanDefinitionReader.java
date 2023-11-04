package com.xjdl.framework.beans.factory.xml;

import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.factory.config.BeanReference;
import com.xjdl.framework.beans.factory.support.AbstractBeanDefinitionReader;
import com.xjdl.framework.beans.factory.support.BeanDefinition;
import com.xjdl.framework.context.annotation.ScopeType;
import com.xjdl.framework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * 类似于 spring.xml 的配置文件
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
	public static final String ID = "id";
	public static final String CLASS = "class";
	public static final String PROPERTY = "property";
	public static final String NAME = "name";
	public static final String VALUE = "value";
	public static final String REF = "ref";
	public static final String SCOPE = "scope";

	public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		InputStream inputStream = getResourceLoader().getUrlInputStreamResource(location).getInputStream();
		doLoadBeanDefinitions(inputStream);
	}

	protected void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(inputStream);
		// 解析bean
		registerBeanDefinitions(doc);
		inputStream.close();
	}

	public void registerBeanDefinitions(Document doc) throws Exception {
		Element root = doc.getDocumentElement();

		parseBeanDefinitions(root);
	}

	protected void parseBeanDefinitions(Element root) throws Exception {
		NodeList nl = root.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}
	}

	protected void processBeanDefinition(Element ele) throws Exception {
		String name = ele.getAttribute(ID);
		String className = ele.getAttribute(CLASS);
		this.getRegistryComponentClassesSet().add(className);
		BeanDefinition beanDefinition = createBeanDefinition(ele, name, className);
		this.getRegistryBeanDefinition().put(name, beanDefinition);
	}

	protected BeanDefinition createBeanDefinition(Element ele, String name, String className) throws Exception {
		BeanDefinition beanDefinition = new BeanDefinition();
		processProperty(ele, beanDefinition);
		beanDefinition.setComponentName(name);
		ScopeType scope = ScopeType.valueOf(ele.getAttribute(SCOPE));
		beanDefinition.setScope(scope);
		beanDefinition.setClazzName(className);
		Class<?> beanClass = getResourceLoader().getContextClassLoader().loadClass(className);
		beanDefinition.setBeanClass(beanClass);
		return beanDefinition;
	}

	private void processProperty(Element ele, BeanDefinition beanDefinition) {
		NodeList propertyNode = ele.getElementsByTagName(PROPERTY);
		for (int i = 0; i < propertyNode.getLength(); i++) {
			Node node = propertyNode.item(i);
			if (node instanceof Element) {
				Element propertyEle = (Element) node;
				String name = propertyEle.getAttribute(NAME);
				String value = propertyEle.getAttribute(VALUE);
				if (value.length() > 0) {
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
				} else {
					String ref = propertyEle.getAttribute(REF);
					if (ref.length() == 0) {
						throw new IllegalArgumentException("Configuration problem: <property> element for property '"
								+ name + "' must specify a ref or value");
					}
					BeanReference beanReference = new BeanReference(ref);
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
				}
			}
		}
	}
}
