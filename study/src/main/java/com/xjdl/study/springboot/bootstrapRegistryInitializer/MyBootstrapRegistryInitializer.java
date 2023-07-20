package com.xjdl.study.springboot.bootstrapRegistryInitializer;

import com.xjdl.study.spi.XJDL;
import com.xjdl.study.springboot.importBean.MyImportSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.core.io.UrlResource;
import org.springframework.core.type.AnnotationMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 感知应用引导阶段开始
 *
 * @see MyImportSelector#selectImports(AnnotationMetadata) 注入IOC容器
 * @see SpringApplication#getSpringFactoriesInstances(Class)
 * @see SpringApplication#createBootstrapContext()
 */
@Slf4j
public class MyBootstrapRegistryInitializer implements BootstrapRegistryInitializer {
    public static final DeferredLog Dlog = new DeferredLog();
    private final String SPI_LOCATION = "META-INF/xjdl/";
    private final String SPI_EXT = ".component";
    public static List<String> SPI_RESULT;

    @Override
    public void initialize(BootstrapRegistry registry) {
//        此时输出的日志拥有不完全的格式，且并非有效日志
//        log.info("com.xjdl.study.springboot.bootstrapRegistryInitializer.MyBootstrapRegistryInitializer.initialize");
        Dlog.info("com.xjdl.study.springboot.bootstrapRegistryInitializer.MyBootstrapRegistryInitializer.initialize");

        // 获取文件路径
        String location = String.format(SPI_LOCATION + "%s" + SPI_EXT, XJDL.class.getName());
        try {
            // 加载资源
            ClassLoader classLoader = getClass().getClassLoader();
            Enumeration<URL> urlEnumeration = classLoader.getResources(location);
            List<String> componentList = new ArrayList<>();
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                componentList.addAll(getComponent(url));
            }
            // 去重
            SPI_RESULT = new ArrayList<>(new LinkedHashSet<>(componentList));
//            for (String component : SPI_RESULT) {
//                Class<?> instanceClass = ClassUtils.forName(component, classLoader);
//                instanceClass.newInstance();
//            }
        } catch (IOException e) {
            log.error("{} component load failed !{}", location, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 一行行读取SPI文件内容
     */
    private List<String> getComponent(URL url) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new UrlResource(url).getInputStream(), StandardCharsets.UTF_8))
        ) {
            List<String> candidates = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String result;
                int commentStart = line.indexOf("#");
                if (commentStart == -1) {
                    result = line;
                } else {
                    result = line.substring(0, commentStart);
                }
                line = result;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                candidates.add(line);
            }
            return candidates;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Get component [" + url + "] failed.", ex);
        }
    }
}
