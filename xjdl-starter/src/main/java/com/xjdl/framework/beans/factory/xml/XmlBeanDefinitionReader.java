package com.xjdl.framework.beans.factory.xml;

import com.xjdl.framework.beans.PropertyValue;
import com.xjdl.framework.beans.factory.BeanDefinitionStoreException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.BeanDefinitionHolder;
import com.xjdl.framework.beans.factory.config.BeanReference;
import com.xjdl.framework.beans.factory.support.AbstractBeanDefinitionReader;
import com.xjdl.framework.beans.factory.support.BeanDefinitionRegistry;
import com.xjdl.framework.core.io.Resource;
import com.xjdl.framework.core.io.ResourceLoader;
import com.xjdl.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * org.springframework.beans.factory.xml.BeanDefinitionParserDelegate
 */
@Slf4j
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String LAZY_INIT_ATTRIBUTE = "lazy-init";
    public static final String TRUE_VALUE = "true";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        ResourceLoader resourceLoader = super.getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        return loadBeanDefinitions(resource);
    }

    @Override
    public int loadBeanDefinitions(Resource resource) {
        int count = 0;
        try (InputStream is = resource.getInputStream()) {
			InputSource inputSource = new InputSource(is);
			count += doLoadBeanDefinitions(inputSource);
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("Exception parsing XML document from " + resource.getPath(), e);
        }
        return count;
    }

    public int loadBeanDefinitions(InputSource inputSource) throws Exception {
        return doLoadBeanDefinitions(inputSource);
    }

    protected int doLoadBeanDefinitions(InputSource inputSource) throws Exception {
        Document doc = doLoadDocument(inputSource);
        return registerBeanDefinitions(doc);
    }

    private Document doLoadDocument(InputSource inputSource) throws Exception {
        return loadDocument(inputSource);
    }

    private Document loadDocument(InputSource inputSource) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        return docBuilder.parse(inputSource);
    }

    private int registerBeanDefinitions(Document doc) {
        int countBefore = getRegistry().getBeanDefinitionCount();
        doRegisterBeanDefinitions(doc.getDocumentElement());
        return getRegistry().getBeanDefinitionCount() - countBefore;
    }

    protected void doRegisterBeanDefinitions(Element root) {
        parseBeanDefinitions(root);
    }

    private void parseBeanDefinitions(Element root) {
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (isCandidateElement(node)) {
                // 外层标签，<bean/>
                parseDefaultElement((Element) node);
            }
        }
    }

    private void parseDefaultElement(Element ele) {
        processBeanDefinition(ele);
    }

    private void processBeanDefinition(Element ele) {
        parseBeanDefinitionElement(ele);
    }

    private void parseBeanDefinitionElement(Element ele) {
        BeanDefinitionHolder bdHolder = parseBeanDefinitionElement(ele, null);
        registerBeanDefinition(bdHolder);
    }

    /**
     * org.springframework.beans.factory.support.BeanDefinitionReaderUtils#registerBeanDefinition(org.springframework.beans.factory.config.BeanDefinitionHolder, org.springframework.beans.factory.support.BeanDefinitionRegistry)
     */
    private void registerBeanDefinition(BeanDefinitionHolder bdHolder) {
        String beanName = bdHolder.getBeanName();
        getRegistry().registerBeanDefinition(beanName, bdHolder.getBeanDefinition());
        if (log.isTraceEnabled()) {
            log.trace("BeanDefinition named '{}' be registered.", beanName);
        }
    }

    /**
     * bean 的 name 属性被放入 BeanDefinitionHolder 中，BeanDefinition 本身没有 beanName 属性
     *
     * @see org.springframework.beans.factory.xml.BeanDefinitionParserDelegate#parseBeanDefinitionElement(org.w3c.dom.Element, org.springframework.beans.factory.config.BeanDefinition)
     */
    private BeanDefinitionHolder parseBeanDefinitionElement(Element ele, BeanDefinition containingBean) {
        // 没写 id 属性则获取的是空串
        String id = ele.getAttribute(ID_ATTRIBUTE);
        String beanName = id;
        BeanDefinition beanDefinition = parseBeanDefinitionElement(ele, beanName, containingBean);
        if (!StringUtils.hasText(beanName)) {
            beanName = generateBeanName(beanDefinition, getRegistry());
        }
        return new BeanDefinitionHolder(beanDefinition, beanName);
    }

    private String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        return getBeanNameGenerator().generateBeanName(beanDefinition, registry);
    }


    private BeanDefinition parseBeanDefinitionElement(Element ele, String beanName, BeanDefinition containingBean) {
        String className = null;
        if (ele.hasAttribute(CLASS_ATTRIBUTE)) {
            className = ele.getAttribute(CLASS_ATTRIBUTE).trim();
        }
        try {
            // bd 本该是 AbstractBeanDefinition
            BeanDefinition bd = createBeanDefinition(className, getBeanClassLoader());
            parseBeanDefinitionAttributes(ele, beanName, containingBean, bd);
            parsePropertyElements(ele, bd);
            return bd;
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("Parsing BeanDefinitionElement Exception", e);
        }
    }

    public BeanDefinition parseBeanDefinitionAttributes(Element ele, String beanName, BeanDefinition containingBean, BeanDefinition bd) {
        if (ele.hasAttribute(SCOPE_ATTRIBUTE)) {
            bd.setScope(ele.getAttribute(SCOPE_ATTRIBUTE));
        } else if (containingBean != null) {
            bd.setScope(containingBean.getScope());
        }
        if (ele.hasAttribute(LAZY_INIT_ATTRIBUTE)) {
            String lazyInit = ele.getAttribute(LAZY_INIT_ATTRIBUTE);
            bd.setLazyInit(TRUE_VALUE.equals(lazyInit));
        }
        if (ele.hasAttribute(INIT_METHOD_ATTRIBUTE)) {
            String initMethodName = ele.getAttribute(INIT_METHOD_ATTRIBUTE);
            bd.setInitMethodName(initMethodName);
        }
        if (ele.hasAttribute(DESTROY_METHOD_ATTRIBUTE)) {
            String destroyMethodName = ele.getAttribute(DESTROY_METHOD_ATTRIBUTE);
            bd.setDestroyMethodName(destroyMethodName);
        }
        return bd;
    }

    private BeanDefinition createBeanDefinition(String className, ClassLoader classLoader) throws ClassNotFoundException {
        BeanDefinition bd = new BeanDefinition();
        if (classLoader != null) {
            bd.setBeanClass(Class.forName(className, false, classLoader));
        }
        bd.setBeanClassName(className);
        return bd;
    }

    private void parsePropertyElements(Element beanEle, BeanDefinition bd) {
        NodeList nl = beanEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            // <bean/> 的内部标签
            Node node = nl.item(i);
            if (isCandidateElement(node) && nodeNameEquals(node, PROPERTY_ELEMENT)) {
                parsePropertyElement((Element) node, bd);
            }
        }
    }

    public void parsePropertyElement(Element ele, BeanDefinition bd) {
        String propertyName = ele.getAttribute(NAME_ATTRIBUTE);
        if (!StringUtils.hasLength(propertyName)) {
            throw new BeanDefinitionStoreException("Tag 'property' must have a 'name' attribute" + ele);
        }
        if (bd.getPropertyValues().contains(propertyName)) {
            throw new BeanDefinitionStoreException("Multiple 'property' definitions for property '" + propertyName + "'" + ele);
        }
        Object val = parsePropertyValue(ele, bd, propertyName);
        PropertyValue pv = new PropertyValue(propertyName, val);
        bd.getPropertyValues().addPropertyValue(pv);
    }

    public Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
        String elementName = (propertyName != null ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element");
        boolean hasRefAttribute = ele.hasAttribute(REF_ATTRIBUTE);
        boolean hasValueAttribute = ele.hasAttribute(VALUE_ATTRIBUTE);
        if (hasRefAttribute & hasValueAttribute) {
            throw new BeanDefinitionStoreException(elementName + " is only allowed to contain either 'ref' attribute OR 'value' attribute OR sub-element" + ele);
        }
        if (hasRefAttribute) {
            String refName = ele.getAttribute(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                throw new BeanDefinitionStoreException(elementName + " contains empty 'ref' attribute" + ele);
            }
            return new BeanReference(refName);
        } else if (hasValueAttribute) {
            String valueAttr = ele.getAttribute(VALUE_ATTRIBUTE);
            if (!StringUtils.hasText(valueAttr)) {
                throw new BeanDefinitionStoreException(elementName + " contains empty 'value' attribute" + ele);
            }
            return valueAttr;
        } else {
            throw new BeanDefinitionStoreException(elementName + " must specify a ref or value" + ele);
        }
    }

    private boolean isCandidateElement(Node node) {
        return node instanceof Element;
    }

    public boolean nodeNameEquals(Node node, String desiredName) {
        return desiredName.equals(node.getNodeName()) || desiredName.equals(getLocalName(node));
    }

    public String getLocalName(Node node) {
        return node.getLocalName();
    }
}
