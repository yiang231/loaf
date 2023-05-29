- [x] Logback + Exception
    - 全局异常处理
    - 切面化
- [x] T...a
- [x] Springboot Quartz
- [x] mysql
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
- [ ] socket
- [x] stream
- [x] ~~logback~~
- [ ] log4j2
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
- [ ] properties
- [x] lambda
- [ ] swagger -> springdoc
- [ ] APM
- [ ] shell
- [x] resource
- [x] junitJupiter
- [ ] mockito
- [ ] coverage
- [ ] Intellij Profiler
- [ ] org.springframework
- [x] SpringTest
- [ ] devtools
- [ ] jmh
- [ ] starter
- [ ] wls
- [ ] docker
- [ ] log4j2
- [ ] log MDC
- [ ] wsl2
- [ ] docker
- [ ] docker compose
- [ ] SpringUtils
- [ ] Spring Cache
- [ ] Caffeine
- [ ] GraalVM
- [ ] Quarkus
- [ ] traceId
- [ ] lang3
- [x] undertow
- [x] init
- [x] fileNotFound
- [ ] Gradle
- [ ] Groovy
- [x] maven

```shell
# 4线程构建
mvn -T 4 clean install
# 每个cpu核心2个线程，比如我是4核cpu那么就8个线程
# [INFO] Using the MultiThreadedBuilder implementation with a thread count of 8
mvn -T 1C clean install
```



