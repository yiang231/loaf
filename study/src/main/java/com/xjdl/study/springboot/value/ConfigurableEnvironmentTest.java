package com.xjdl.study.springboot.value;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.Resource;

/**
 * 获取配置参数
 */
@SpringBootTest
@Slf4j
public class ConfigurableEnvironmentTest {
    @Resource
    ConfigurableEnvironment configurableEnvironment;

    /**
     * getSystemEnvironment USERDOMAIN_ROAMINGPROFILE = DESKTOP-2MNE2BS
     * getSystemEnvironment PROCESSOR_LEVEL = 6
     * getSystemEnvironment SESSIONNAME = Console
     * getSystemEnvironment ALLUSERSPROFILE = C:\ProgramData
     * getSystemEnvironment PROCESSOR_ARCHITECTURE = AMD64
     * getSystemEnvironment GATEWAY_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\gateway.vmoptions
     * getSystemEnvironment PSModulePath = C:\Program Files\WindowsPowerShell\Modules;C:\Windows\system32\WindowsPowerShell\v1.0\Modules
     * getSystemEnvironment SystemDrive = C:
     * getSystemEnvironment RIDER_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\rider.vmoptions
     * getSystemEnvironment USERNAME = Administrator
     * getSystemEnvironment DEVECOSTUDIO_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\devecostudio.vmoptions
     * getSystemEnvironment STUDIO_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\studio.vmoptions
     * getSystemEnvironment ProgramFiles(x86) = C:\Program Files (x86)
     * getSystemEnvironment FPS_BROWSER_USER_PROFILE_STRING = Default
     * getSystemEnvironment PATHEXT = .COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC
     * getSystemEnvironment APPCODE_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\appcode.vmoptions
     * getSystemEnvironment DriverData = C:\Windows\System32\Drivers\DriverData
     * getSystemEnvironment DATASPELL_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\dataspell.vmoptions
     * getSystemEnvironment ProgramData = C:\ProgramData
     * getSystemEnvironment ProgramW6432 = C:\Program Files
     * getSystemEnvironment HOMEPATH = \Users\Administrator
     * getSystemEnvironment PROCESSOR_IDENTIFIER = Intel64 Family 6 Model 94 Stepping 3, GenuineIntel
     * getSystemEnvironment ProgramFiles = C:\Program Files
     * getSystemEnvironment PUBLIC = C:\Users\Public
     * getSystemEnvironment windir = C:\Windows
     * getSystemEnvironment =:: = ::\
     * getSystemEnvironment DATAGRIP_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\datagrip.vmoptions
     * getSystemEnvironment LOCALAPPDATA = C:\Users\Administrator\AppData\Local
     * getSystemEnvironment USERDOMAIN = DESKTOP-2MNE2BS
     * getSystemEnvironment FPS_BROWSER_APP_PROFILE_STRING = Internet Explorer
     * getSystemEnvironment LOGONSERVER = \\DESKTOP-2MNE2BS
     * getSystemEnvironment PYCHARM_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\pycharm.vmoptions
     * getSystemEnvironment WEBSTORM_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\webstorm.vmoptions
     * getSystemEnvironment JAVA_HOME = E:\JDK1.6\jdk1.6.0_18
     * getSystemEnvironment CLION_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\clion.vmoptions
     * getSystemEnvironment JETBRAINSCLIENT_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\jetbrainsclient.vmoptions
     * getSystemEnvironment GOLAND_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\goland.vmoptions
     * getSystemEnvironment APPDATA = C:\Users\Administrator\AppData\Roaming
     * getSystemEnvironment IDEA_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\idea.vmoptions
     * getSystemEnvironment RUBYMINE_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\rubymine.vmoptions
     * getSystemEnvironment JETBRAINS_CLIENT_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\jetbrains_client.vmoptions
     * getSystemEnvironment CommonProgramFiles = C:\Program Files\Common Files
     * getSystemEnvironment Path = E:\JDK1.6\jdk1.6.0_18\bin;E:\oracle11\app\oracle\product\11.2.0\server\bin;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Windows\SysWOW64\4097;C:\Windows\SysWOW64;E:\instantclient_19_8\NETWORK\ADMIN;E:\tomcat\PortableGit\bin;C:\Users\Administrator\AppData\Local\Microsoft\WindowsApps;
     * getSystemEnvironment OS = Windows_NT
     * getSystemEnvironment COMPUTERNAME = DESKTOP-2MNE2BS
     * getSystemEnvironment PROCESSOR_REVISION = 5e03
     * getSystemEnvironment CommonProgramW6432 = C:\Program Files\Common Files
     * getSystemEnvironment ComSpec = C:\Windows\system32\cmd.exe
     * getSystemEnvironment SystemRoot = C:\Windows
     * getSystemEnvironment TEMP = C:\Users\Administrator\AppData\Local\Temp
     * getSystemEnvironment WEBIDE_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\webide.vmoptions
     * getSystemEnvironment HOMEDRIVE = C:
     * getSystemEnvironment USERPROFILE = C:\Users\Administrator
     * getSystemEnvironment TMP = C:\Users\Administrator\AppData\Local\Temp
     * getSystemEnvironment CommonProgramFiles(x86) = C:\Program Files (x86)\Common Files
     * getSystemEnvironment NUMBER_OF_PROCESSORS = 4
     * getSystemEnvironment PHPSTORM_VM_OPTIONS = E:\tomcat\ide\jetbra\vmoptions\phpstorm.vmoptions
     * getSystemEnvironment IDEA_INITIAL_DIRECTORY = E:\tomcat\ide\ideaIU-2022.3.2.win\bin
     * getSystemProperties sun.cpu.isalist = amd64
     * getSystemProperties sun.desktop = windows
     * getSystemProperties sun.io.unicode.encoding = UnicodeLittle
     * getSystemProperties sun.cpu.endian = little
     * getSystemProperties idea.test.cyclic.buffer.size = 1048576
     * getSystemProperties java.vendor.url.bug = http://bugreport.sun.com/bugreport/
     * getSystemProperties file.separator = \
     * getSystemProperties com.zaxxer.hikari.pool_number = 1
     * getSystemProperties java.awt.headless = true
     * getSystemProperties java.vendor = Oracle Corporation
     * getSystemProperties sun.boot.class.path = E:\Java\jdk1.8.0_201\jre\lib\resources.jar;E:\Java\jdk1.8.0_201\jre\lib\rt.jar;E:\Java\jdk1.8.0_201\jre\lib\sunrsasign.jar;E:\Java\jdk1.8.0_201\jre\lib\jsse.jar;E:\Java\jdk1.8.0_201\jre\lib\jce.jar;E:\Java\jdk1.8.0_201\jre\lib\charsets.jar;E:\Java\jdk1.8.0_201\jre\lib\jfr.jar;E:\Java\jdk1.8.0_201\jre\classes
     * getSystemProperties java.ext.dirs = E:\Java\jdk1.8.0_201\jre\lib\ext;C:\Windows\Sun\Java\lib\ext
     * getSystemProperties java.version = 1.8.0_201
     * getSystemProperties java.vm.info = mixed mode
     * getSystemProperties awt.toolkit = sun.awt.windows.WToolkit
     * getSystemProperties user.language = zh
     * getSystemProperties java.specification.vendor = Oracle Corporation
     * getSystemProperties sun.java.command = com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5 com.xjdl.study.springboot.value.ConfigurableEnvironmentTest,property
     * getSystemProperties java.home = E:\Java\jdk1.8.0_201\jre
     * getSystemProperties sun.arch.data.model = 64
     * getSystemProperties java.vm.specification.version = 1.8
     * getSystemProperties user.name = Administrator
     * getSystemProperties file.encoding = UTF-8
     * getSystemProperties java.specification.version = 1.8
     * getSystemProperties java.awt.printerjob = sun.awt.windows.WPrinterJob
     * getSystemProperties user.timezone = Asia/Shanghai
     * getSystemProperties user.home = C:\Users\Administrator
     * getSystemProperties os.version = 10.0
     * getSystemProperties sun.management.compiler = HotSpot 64-Bit Tiered Compilers
     * getSystemProperties java.specification.name = Java Platform API Specification
     * getSystemProperties java.class.version = 52.0
     * getSystemProperties spring.beaninfo.ignore = true
     * getSystemProperties java.library.path = E:\Java\jdk1.8.0_201\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;E:\JDK1.6\jdk1.6.0_18\bin;E:\oracle11\app\oracle\product\11.2.0\server\bin;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Windows\SysWOW64\4097;C:\Windows\SysWOW64;E:\instantclient_19_8\NETWORK\ADMIN;E:\tomcat\PortableGit\bin;C:\Users\Administrator\AppData\Local\Microsoft\WindowsApps;;.
     * getSystemProperties sun.jnu.encoding = GBK
     * getSystemProperties FILE_LOG_CHARSET = UTF-8
     * getSystemProperties os.name = Windows 10
     * getSystemProperties user.variant =
     * getSystemProperties java.vm.specification.vendor = Oracle Corporation
     * getSystemProperties java.io.tmpdir = C:\Users\Administrator\AppData\Local\Temp\
     * <p>
     * getSystemProperties CONSOLE_LOG_CHARSET = UTF-8
     * getSystemProperties java.endorsed.dirs = E:\Java\jdk1.8.0_201\jre\lib\endorsed
     * getSystemProperties os.arch = amd64
     * getSystemProperties java.awt.graphicsenv = sun.awt.Win32GraphicsEnvironment
     * getSystemProperties java.runtime.version = 1.8.0_201-b09
     * getSystemProperties PID = 8736
     * getSystemProperties java.vm.specification.name = Java Virtual Machine Specification
     * getSystemProperties user.dir = E:\tomcat\MyTest\springboot\study
     * getSystemProperties user.country = CN
     * getSystemProperties user.script =
     * getSystemProperties sun.java.launcher = SUN_STANDARD
     * getSystemProperties sun.os.patch.level =
     * getSystemProperties java.vm.name = Java HotSpot(TM) 64-Bit Server VM
     * getSystemProperties file.encoding.pkg = sun.io
     * getSystemProperties path.separator = ;
     * getSystemProperties java.vm.vendor = Oracle Corporation
     * getSystemProperties java.vendor.url = http://java.oracle.com/
     * getSystemProperties sun.boot.library.path = E:\Java\jdk1.8.0_201\jre\bin
     * getSystemProperties java.vm.version = 25.201-b09
     * getSystemProperties java.runtime.name = Java(TM) SE Runtime Environment
     * getPropertySources ConfigurationPropertySourcesPropertySource {name='configurationProperties'}
     * getPropertySources MapPropertySource {name='test'}
     * getPropertySources MapPropertySource {name='Inlined Test Properties'}
     * getPropertySources StubPropertySource {name='servletConfigInitParams'}
     * getPropertySources ServletContextPropertySource {name='servletContextInitParams'}
     * getPropertySources PropertiesPropertySource {name='systemProperties'}
     * getPropertySources OriginAwareSystemEnvironmentPropertySource {name='systemEnvironment'}
     * getPropertySources RandomValuePropertySource {name='random'}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-undertow.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-task.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-springdoc.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-mybatisplus.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-jpa.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-jackson.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-hikari.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-datasource.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application-adminclient.yml]' via location 'optional:classpath:/''}
     * getPropertySources OriginTrackedMapPropertySource {name='Config resource 'class path resource [application.yaml]' via location 'optional:classpath:/''}
     * getPropertySources ResourcePropertySource {name='class path resource [remote.yaml]'}
     * getPropertySources ResourcePropertySource {name='class path resource [remote.properties]'}
     * http://127.0.0.1:9999
     */
	// getSystemProperties java.class.path = C:\Users\Administrator\.m2\repository\..
	// classpath*:/mapper/**/*.xml
    // Administrator
    @Test
    public void property() {
        configurableEnvironment.getSystemEnvironment().forEach((s, o) -> log.info("getSystemEnvironment {} = {}", s, o));
        configurableEnvironment.getSystemProperties().forEach((s, o) -> log.info("getSystemProperties {} = {}", s, o));
        configurableEnvironment.getPropertySources().stream().forEach(propertySource -> log.info("getPropertySources {}", propertySource));
        log.info(configurableEnvironment.getProperty("spring.boot.admin.client.url"));
        log.info(configurableEnvironment.getProperty("mybatis-plus.mapper-locations"));
        log.info(configurableEnvironment.getProperty("user.name"));
        log.info(configurableEnvironment.getProperty("user.dir"));
    }
}
