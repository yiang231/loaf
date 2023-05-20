package com.xjdl.study.reflect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 反射
 * java.lang.reflect
 */
@Slf4j
@Data
public class ReflectionTest implements Serializable {
    static boolean alive = true;
    public int age = 9;
    protected String name;
    private List<Long> longList;

//    public ReflectionTest(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }

    public ReflectionTest() {
    }

    /**
     * 得到具体的构造函数
     */
    public static void main(String[] args) {
        try {
            Constructor<ReflectionTest> constructor = ReflectionTest.class.getConstructor(String.class, int.class);
            log.info("{}", constructor);
            ReflectionTest test = constructor.newInstance("Hello World", 9);
            log.info("{}", test);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

    protected String print(@NotNull String str) {
        return str;
    }

    protected List<String> generic(List<Integer> list) {
        return list.stream().parallel().map(String::valueOf).collect(Collectors.toList());
    }

    /**
     * 获得Class对象
     */
    @Test
    void getKlass() throws ClassNotFoundException {
        log.info("通过 Class.forName 获得 {}", Class.forName("com.xjdl.study.reflect.ReflectionTest"));
        log.info("通过 ClassName.class 获得 {}", ReflectionTest.class);
        log.info("包装类通过 TYPE 来获得 {}", Integer.TYPE);

        log.info("通过对象的 getClass() 方法来获得 {}", this.getClass());

        log.info("获得Void对象 {}", Void.class);
        log.info("获得Void对象 {}", Void.TYPE);
    }

    /**
     * 基操
     */
    @Test
    void getKlassField() {
        try {
            int modifiers = getClass().getModifiers();
            log.info("{}", modifiers);
            log.info("{}", Modifier.isPublic(Modifier.PUBLIC));
            log.info("{}", Modifier.isPublic(modifiers));
            log.info("{}", Modifier.isAbstract(modifiers));

            log.info("{}", getClass().getPackage());

            log.info("{}", getClass().getSuperclass());

            log.info("{}", (Object) getClass().getInterfaces());

            log.info("{}", (Object) getClass().getConstructors());

            Arrays.stream(getClass().getDeclaredFields()).forEach(field -> log.info("{}", field));
            log.info("{}", getClass().getDeclaredField("log").getName());

            Arrays.stream(getClass().getDeclaredMethods()).forEach(method -> log.info("{}", method));
            log.info("{}", getClass().getDeclaredMethod("getKlassField"));

            ReflectionTest reflectionTest = getClass().newInstance();
            log.info("{}", reflectionTest);
            log.info("{}", getClass().isInstance(reflectionTest));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取方法参数
     */
    @Test
    void methodParameters() {
        try {
            Method print = getClass().getDeclaredMethod("print", String.class);
            log.info("参数个数 {}", print.getParameterCount());
            Class<?>[] parameterTypes = print.getParameterTypes();
            Arrays.stream(parameterTypes).forEach(type -> log.info("参数类型 {}", type));
            Parameter[] parameters = print.getParameters();
            Arrays.stream(parameters).forEach(parameter -> log.info("参数名 {}, 修饰符个数 {}, 注解 {}", parameter, parameter.getModifiers(), parameter.getAnnotations()));
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 反射调用方法
     */
    @Test
    void useMethod() {
        try {
            ReflectionTest reflectionTest = this.getClass().newInstance();
            Method getKlassField = getClass().getDeclaredMethod("getKlassField");
            getKlassField.invoke(reflectionTest, null);
            Method print = getClass().getDeclaredMethod("print", String.class);
//        print.setAccessible(true);
            log.info("{}", print.invoke(reflectionTest, "print() 方法执行"));
//        print.setAccessible(false);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 反射获取变量
     */
    @Test
    void useField() {
        try {
            ReflectionTest reflectionTest = getClass().newInstance();

            Field[] declaredFields = getClass().getDeclaredFields();
            Arrays.stream(declaredFields).forEach(field -> log.info("修饰符个数 {} 属性类型 {} 属性名 {}", field.getModifiers(), field.getType(), field.getName()));

            Field nameField = getClass().getDeclaredField("name");
//            nameField.setAccessible(true);
            log.info("获得实例变量 {}", nameField.get(reflectionTest));
            nameField.set(reflectionTest, "newName");
            log.info("获得实例变量 {}", nameField.get(reflectionTest));
//            nameField.setAccessible(false);

            Field ageField = getClass().getDeclaredField("age");
            log.info("获得实例变量 {}", ageField.getInt(reflectionTest));
            Field aliveField = getClass().getDeclaredField("alive");
            log.info("获得静态变量 {}", aliveField.getBoolean(reflectionTest));
            log.info("获得静态变量，对象可以传null {}", aliveField.get(null));
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * java.lang.reflect.Array
     * 数组与反射
     */
    @Test
    void array() {
        Object instance = Array.newInstance(String.class, 9);
        Array.set(instance, 6, "array");
        log.info("{}", instance);
    }

    /**
     * 泛型与反射
     */
    @Test
    void generic() {
        try {
            Method genericMethod = getClass().getDeclaredMethod("generic", List.class);
            Class<?> returnType = genericMethod.getReturnType();
            log.info("returnType {}", returnType);
            Type genericReturnType = genericMethod.getGenericReturnType();
            log.info("genericReturnType {}", genericReturnType);
            Type[] genericParameterTypes = genericMethod.getGenericParameterTypes();
            Arrays.stream(genericParameterTypes).forEach(item -> log.info("genericParameterTypes {}", item));
            ParameterizedType parameterizedMethodType = (ParameterizedType) genericReturnType;
            Arrays.stream(parameterizedMethodType.getActualTypeArguments()).forEach(item -> log.info("getMethodActualTypeArguments {}", item));

            Field listField = getClass().getDeclaredField("longList");
            Type genericType = listField.getGenericType();
            log.info("genericType {}", genericType);
            ParameterizedType parameterizedFieldType = (ParameterizedType) genericType;
            Arrays.stream(parameterizedFieldType.getActualTypeArguments()).forEach(item -> log.info("getFieldActualTypeArguments {}", item));
        } catch (NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    void reflectCase() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("id", 32);
            put("name", "le");
            put("salary", 3200.00);
        }};
        log.info("{}", copyProperties(map, TargetEntity.class));
    }

    /**
     * 实现类似 BeanUtils.copyProperties() 的功能
     *
     * @param map          对象参数容器
     * @param targetEntity 目标对象的Class
     * @param <T>          目标对象的类型
     * @return 目标对象
     */
    <T> T copyProperties(Map<String, Object> map, Class<T> targetEntity) {
        T instance = null;
        try {
            instance = targetEntity.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Field field = targetEntity.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(instance, entry.getValue());
                field.setAccessible(false);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            log.warn("没有 {} 这个属性", e.getMessage());
        }
        return instance;
    }
    /**
     * 反射获取注解信息见
     * com.xjdl.study.annotation.AnnotationTest.print
     */
}


