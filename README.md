- [x] Logback + Exception
    - 全局异常处理
    - 切面化
- [x] T...a
- [x] GitHub Actions Deploy
- [x] 自定义starter
- [ ] 断点续传以及多线程下载
- [x] 自定义banner
- [x] Spring生命周期
- [x] Spring事件开发
- [x] Spring SPI
- [x] System.out转日志
- [x] Spring Function Web
- [x] Spring 配置补全
- [x] Springboot Quartz
- [x] Files Paths
- [x] mysql
- [x] Optional
- [x] package-info
- [ ] MultiDataSource
- [x] admin
- [x] druid
- [ ] prometheus
- [x] annotation
- [x] BigDecimal
- [x] yaml
- [x] Generic
- [x] proxy
- [x] Spring AOP
    - 场景一：记录日志
    - 场景二：监控方法运行时间
        - （监控性能）
    - 场景三：权限控制
    - 场景四：缓存优化
        - （第一次调用查询数据库，将查询结果放入内存对象， 第二次调用， 直接从内存对象返回，不需要查询数据库 ）
    - 场景五：事务管理
        - （调用方法前开启事务， 调用方法后提交关闭事务）
    - 场景六：协议转换
- [x] reflect
- [x] RestTemplate
- [x] WebClient
- [ ] socket
- [x] stream
- [x] ~~logback~~
- [x] log4j2
- [ ] tx
- [x] jpa
- [ ] webflux
- [ ] netty
- [ ] hibernate
- [x] MybatisPlus
- [ ] mapper.xml tags
- [ ] sqlite
- [ ] xxl-job
- [ ] ELK
- [x] properties
- [x] lambda
- [ ] swagger -> springdoc
- [ ] APM
- [ ] shell
- [ ] SpEL
- [x] cron
- [x] resource
- [x] junitJupiter
- [ ] mockito
- [ ] coverage
- [ ] Intellij Profiler
- [ ] org.springframework
- [x] SpringTest
- [ ] devtools
- [ ] jmh
- [ ] docker
- [ ] log4j2
- [x] log MDC
- [ ] wsl2
- [ ] docker
- [ ] docker compose
- [ ] SpringUtils
- [x] Spring Cache
- [x] Caffeine
  - API
  - 创建，读取
  - 设置为默认的缓存管理器
- [ ] GraalVM
- [ ] Quarkus
- [ ] traceId
- [ ] lang3
- [x] undertow
- [x] init
- [x] fileNotFound
- [ ] Gradle
- [ ] Groovy
- [x] @Import
- [ ] Spring Validation
- [x] Spring MVC 消息协商
- [ ] @ResponseStatus
- [x] maven
- [x] SPI
- [x] design patterns
  - behavioral
    - 责任链
    - 组合
    - 迭代器
    - 观察者
    - 策略
    - 模板
  - creational
    - 简单工厂
    - 工厂方法
    - 抽象工厂
    - 单例
  - structural
    - 适配器
    - 装饰器
    - 门面
    - 代理

```shell
# 4线程构建
mvn -T 4 clean install
# 每个cpu核心2个线程，比如我是4核cpu那么就8个线程
# [INFO] Using the MultiThreadedBuilder implementation with a thread count of 8
mvn -T 1C clean install
```
Live Templates

| Description | Abbreviation | Template text                                    | Applicable        | Variables                         |
| ----------- | ------------ | ------------------------------------------------ | ----------------- | --------------------------------- |
| 快速日志    | ll           | `log.$LEVEL$("$WORD${}", $MSG$);`                | Java: statement   | LEVEL: completeSmart()            |
| 快速测试    | tt           | @Test<br>void `$METHODNAME$`() {<br>`$END$`<br>} | Java: declaration | METHODNAME: suggestVariableName() |
