package com.xjdl.study.myBatisPlus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjdl.study.exception.globalException.ResultResponse;
import com.xjdl.study.myBatisPlus.mapper.MybatisPlusUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@Slf4j
@RestController
@RequestMapping("/mpuser")
public class MybatisPlusUserController {
	@Autowired
	public MybatisPlusUserService mybatisPlusUserService;
	@Resource
	private MybatisPlusUserMapper mybatisPlusUserMapper;

	/**
	 * 查询所有
	 */
	@Test
	public void test1() {
		List<MybatisPlusUser> list = mybatisPlusUserMapper.selectList(null);
		list.forEach(mybatisPlusUser -> log.info("{}", mybatisPlusUser));
	}

	/**
	 * 插入数据使用默认算法生成主键
	 */
	@Test
	public void test2() {
		MybatisPlusUser mybatisPlusUser = new MybatisPlusUser();
		mybatisPlusUser.setName("张三");
		mybatisPlusUser.setAge(30);
		mybatisPlusUser.setEmail("123@qq.com");

		//添加数据
		mybatisPlusUserMapper.insert(mybatisPlusUser);
		//id 1587062368762974210 长度19的Long型

		//主键自动回填
		//mybatisPlusUser = MybatisPlusUser(id=1587063016820600833, name=张三, age=30, email=123@qq.com)
		log.info("{}", mybatisPlusUser);
	}

	/**
	 * 查询出数据并且更新数据——实际使用的是动态SQL
	 */
	@Test
	public void test3() {
//		userMapper.updateById(entity);//根据主键修改
//		userMapper.update(mybatisPlusUser, queryWrapper);//根据条件进行修改
		MybatisPlusUser mybatisPlusUser = mybatisPlusUserMapper.selectById(1L);
		log.info("user修改前 {}", mybatisPlusUser);//此时User被查询出来，所有字段不为空

		mybatisPlusUser.setName("李四");
		mybatisPlusUser.setAge(21);
		mybatisPlusUserMapper.updateById(mybatisPlusUser);//这是动态SQL
		log.info("user修改后 {}", mybatisPlusUser);
		/*所有字段都有值？取决于这个字段是否为空，有就出现
        ==>  Preparing: UPDATE mp_user SET name=?, age=?, update_time=? WHERE id=? AND is_deleted=0
        ==> Parameters: 李四(String), 21(Integer), null, 1(Long)
        <==    Updates: 1
		* */
	}

	/**
	 * 创建对象根据条件修改数据
	 */
	@Test
	public void test4() {
		MybatisPlusUser mybatisPlusUser = new MybatisPlusUser();

		mybatisPlusUser.setName("goushidan");
		mybatisPlusUser.setId(1L);
		/*
		==>  Preparing: UPDATE mp_user SET name=?, update_time=? WHERE id=? AND is_deleted=0
        ==> Parameters: goushidan(String), null, 1(Long)
        <==    Updates: 1
		* */
	}

	/**
	 * createTime以及updateTime实现在insert或者update时自动赋值填充
	 * <p>
	 * 填充原理是直接给entity的属性设置值!!!
	 * 注解则是指定该属性在对应情况下必有值,如果无值则入库会是null
	 * MetaObjectHandler提供的默认方法的策略均为:如果属性有值则不覆盖,如果填充值为null则不填充
	 * 字段必须声明TableField注解,属性fill选择对应策略,该声明告知Mybatis-Plus需要预留注入SQL字段
	 * 填充处理器MyMetaObjectHandler在 Spring Boot 中需要声明@Component或@Bean注入
	 * 要想根据注解FieldFill.xxx和字段名以及字段类型来区分必须使用父类的strictInsertFill或者strictUpdateFill方法
	 * 不需要根据任何来区分可以使用父类的fillStrategy方法
	 * update(T t,Wrapper updateWrapper)时t不能为空,否则自动填充失效
	 */
	@Test
	public void test5() {
		MybatisPlusUser mybatisPlusUser = new MybatisPlusUser();

		mybatisPlusUser.setName("lisi");
		mybatisPlusUser.setAge(21);
		mybatisPlusUser.setEmail("213@qq.com");
		mybatisPlusUser.setCreateTime(new Date());//固定不变
		mybatisPlusUser.setUpdateTime(new Date());//每次修改就要重新赋值

		mybatisPlusUserMapper.insert(mybatisPlusUser);
		log.info("插入数据后 {}", mybatisPlusUser);

		mybatisPlusUserMapper.selectById(mybatisPlusUser.getId());
		log.info("查询出插入的数据 {}", mybatisPlusUser);

		mybatisPlusUser.setAge(2);
		mybatisPlusUser.setUpdateTime(new Date());//每次修改就要重新赋值
		log.info("查询完再次重新赋值 {}", mybatisPlusUser);
	}

	/**
	 * 插入数据时间日期自动更新
	 */
	@Test
	public void test6() {
		MybatisPlusUser mybatisPlusUser = new MybatisPlusUser();

		mybatisPlusUser.setName("fuck");
		mybatisPlusUser.setAge(221);
		mybatisPlusUser.setEmail("2122223@qq.com");
		//添加完对应的类实现MetaObjectHandler后不需要再写
		// MetaObjectHandler提供的默认方法的策略均为:如果属性有值则不覆盖,如果填充值为null则不填充
//		mybatisPlusUser.setCreateTime(new Date());
//		mybatisPlusUser.setUpdateTime(new Date());
		mybatisPlusUserMapper.insert(mybatisPlusUser);
		log.info("{}", mybatisPlusUser);
	}

	/**
	 * 修改数据时间日期自动更新
	 */
	@Test
	public void test7() {
		MybatisPlusUser mybatisPlusUser = new MybatisPlusUser();

		mybatisPlusUser.setId(1654135109739175938L);
		mybatisPlusUser.setAge(2211);

		mybatisPlusUserMapper.updateById(mybatisPlusUser);
		log.info("{}", mybatisPlusUser);
	}

	/**
	 * 验证乐观锁配置生效
	 * 支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
	 * 整数类型下 newVersion = oldVersion + 1
	 * newVersion 会回写到 entity 中
	 * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
	 * 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
	 */
	@Test
	public void test9() {
		MybatisPlusUser mybatisPlusUser = mybatisPlusUserMapper.selectById(1587426270780645377L);
		log.info("{}", mybatisPlusUser);

		mybatisPlusUser.setEmail("3131@qq.com");
		mybatisPlusUserMapper.updateById(mybatisPlusUser);
		log.info("{}", mybatisPlusUser);
		/*
        ==>  Preparing: UPDATE mp_user SET name=?, age=?, email=?, create_time=?, update_time=?, version=? WHERE id=? AND version=? AND is_deleted=0
        ==> Parameters: 配置乐观锁(String), 30(Integer), 3131@qq.com(String), 2022-11-01 20:48:11.0(Timestamp), 2022-11-01 20:50:26.0(Timestamp), 4(Integer), 1587426270780645377(Long), 3(Integer)
        <==    Updates: 1
        MybatisPlusUser(id=1587426270780645377, name=配置乐观锁, age=30, exist=null, email=3131@qq.com, createTime=Tue Nov 01 20:48:11 CST 2022, updateTime=Tue Nov 01 20:50:26 CST 2022, version=4, isDeleted=0)
		* 此时将version手动改为3，查出来的版本号为2，条件不成立，就不会修改，Updates: 0
		* */
	}

	/**
	 * API概览
	 */
	@Test
	public void test10() {
//		//queryWrapper查询条件 仅仅只是条件查询，不可以分页
//		List<MybatisPlusUser> selectList = mybatisPlusUserMapper.selectList(queryWrapper);//将每一行查询到的数据转换为User对象
//		//根据id查询
//		MybatisPlusUser selectById = mybatisPlusUserMapper.selectById(id);
//		//满足queryWrapper的数据一共多少行
//		Integer selectCount = mybatisPlusUserMapper.selectCount(queryWrappr);
//		//根据指定条件查询返回一个对象	注意：如果该条件对应多条数据则会报错
//		MybatisPlusUser selectOne = mybatisPlusUserMapper.selectOne(queryWrappr);
//		//根据集合查询
//		List<MybatisPlusUser> selectBatchIds = mybatisPlusUserMapper.selectBatchIds(Arrays.asList(1, 2, 3));
//		//带条件的分页查询    重要
//		IPage<MybatisPlusUser> selectPage = mybatisPlusUserMapper.selectPage(page, queryWrapper);
//
//		Map<String, Object> map = new HashMap<>();
//		map.put("age", 20);// 表中的列名
//		map.put("name", "张三");
//		List<MybatisPlusUser> selectByMap = mybatisPlusUserMapper.selectByMap(map);//where age = 20 and name = '张三'    （了解）
//
//		List<Map<String, Object>> selectMaps = mybatisPlusUserMapper.selectMaps(queryWrapper);//将每一行查询到的数据转换为map对象	了解
//
//		IPage<Map<String, Object>> selectMapsPage = mybatisPlusUserMapper.selectMapsPage(page, queryWrapper);//将每行查询到的数据转换为map对象	了解
	}

	/**
	 * 用mapper.selectPage()返回的Page对象进行分页查询
	 */
	@Test
	public void test11() {
		long pageNum = 1;//当前页
		long pageSize = 3;//每页条数
		Page<MybatisPlusUser> page = new Page<>(pageNum, pageSize);

		QueryWrapper<MybatisPlusUser> queryWrapper = null;//查询条件

		Page<MybatisPlusUser> pageResult = mybatisPlusUserMapper.selectPage(page, queryWrapper);

		log.info("两个Page对象是否相同 {}", page == pageResult);//true

		List<MybatisPlusUser> records = pageResult.getRecords();//当前页数据
		log.info("总记录数 {}", pageResult.getTotal());
		log.info("当前第几条 {}", pageResult.getCurrent());
		log.info("每页多少条 {}", pageResult.getSize());

		records.forEach(record -> log.info("{}", record));
	}

	//结果为true表明，传什么返回什么，类似于以下操作
//    public Page<MybatisPlusUser> selectPage(Page<MybatisPlusUser> page) {
//        return page;
//    }

	/**
	 * 用原始的Page对象进行分页查询
	 */
	@Test
	public void test12() {
		long pageNum = 2;//当前页
		long pageSize = 3;//每页条数
		Page<MybatisPlusUser> pageParma = new Page<>(pageNum, pageSize);
		QueryWrapper<MybatisPlusUser> queryWrapper = null;//查询条件

		mybatisPlusUserMapper.selectPage(pageParma, queryWrapper);

		List<MybatisPlusUser> records = pageParma.getRecords();//当前页数据
		/*
		LIMIT m,n   m = (page-1)*limit = (1-1)*3 = 从第几条开始
        ==>  Preparing: SELECT id,name,age,email,create_time,update_time,version,is_deleted FROM mp_user WHERE is_deleted=0 LIMIT ?,?
        ==> Parameters: 3(Long), 3(Long)
        <==      Total: 3
		* */
		log.info("总记录数 {}", pageParma.getTotal());
		log.info("当前第几条 {}", pageParma.getCurrent());
		log.info("每页多少条 {}", pageParma.getSize());
		log.info("总页数 {}", pageParma.getPages());
		log.info("是否有下一页 {}", pageParma.hasNext());
		log.info("是否有上一页 {}", pageParma.hasPrevious());

		records.forEach(record -> log.info("{}", record));
	}

	/**
	 * 批量删除，等值删除数据
	 */
	@Test
	public void test13() {
//		int delete = userMapper.delete(wrapper);//条件删除
//		int deleteById = userMapper.deleteById();
        /*
        ==>  Preparing: UPDATE mp_user SET is_deleted=1 WHERE id IN ( ? , ? , ? ) AND is_deleted=0
        ==> Parameters: 1(Integer), 2(Integer), 3(Integer)
        <==    Updates: 3
        */
		mybatisPlusUserMapper.deleteBatchIds(Arrays.asList(1, 2, 3));

		Map<String, Object> map = new HashMap<String, Object>() {{
			//根据key=value的形式，等值删除
			put("age", 23);
		}};
        /*
        ==>  Preparing: UPDATE mp_user SET is_deleted=1 WHERE age = ? AND is_deleted=0
        ==> Parameters: 23(Integer)
        <==    Updates: 1
        */
		//了解即可 delete from user where age = ?;
		mybatisPlusUserMapper.deleteByMap(map);
	}

	/**
	 * 逻辑删除
	 */
	@Test
	public void test14() {
		//userMapper.deleteById(1);
		MybatisPlusUser mybatisPlusUser = mybatisPlusUserMapper.selectById(1);
		mybatisPlusUser.setAge(190);
		mybatisPlusUserMapper.updateById(mybatisPlusUser);
		/*
		==>  Preparing: UPDATE mp_user SET name=?, age=?, email=?, update_time=? WHERE id=? AND is_deleted=0
        ==> Parameters: goushidan(String), 190(Integer), test1@baomidou.com(String), 2022-11-03 10:52:55.0(Timestamp), 1(Long)
        <==    Updates: 1
		*/
		log.info("{}", mybatisPlusUser);
		/*
		* ==>  Preparing: UPDATE mybatisPlusUser SET is_deleted=1 WHERE id=? AND is_deleted=0
		  ==> Parameters: 1(Integer)
		is_deleted = 0 时才会被改为 1，这个字段需要默认值，否则就是空指针异常
		* */
		/*
		 * 给字段赋予默认值两种方式
		 * 1、MetaObjectHandler处理器赋予默认值，只有新增或者修改的数据才会有默认值
		 * 2、手动在表结构中进行定义
		 * */
	}

	/**
	 * 条件构造器queryWrapper1
	 */
	@Test
	public void test15() {
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("age", 21);//等于  column `列名`  value 值  //WHERE is_deleted=0 AND (age = ?)
//		queryWrapper.ne();//不等于
		queryWrapper.like("name", "i");//模糊查询  //WHERE is_deleted=0 AND (age = ? AND name LIKE ?)  //%i%
		queryWrapper.or().likeLeft("name", "i");//%i	表示条件或or的关系，不写默认为与and
		queryWrapper.or().likeRight("name", "i");//i%	//WHERE is_deleted=0 AND (age = ? AND name LIKE ? OR name LIKE ? OR name LIKE ?)
//		queryWrapper.ge();//大于等于
//		queryWrapper.le();//小于等于
//		queryWrapper.gt();//大于
//		queryWrapper.lt();//小于
//		queryWrapper.isNull();//查询字段为空的
//		queryWrapper.between("age", 1, 34);
//		queryWrapper.notBetween("age", 1, 34);//范围查询
//		queryWrapper.in("age", 21, 12, 190);//WHERE is_deleted=0 AND (age = ? AND name LIKE ? OR name LIKE ? OR name LIKE ? AND age IN (?,?,?))
//		queryWrapper.notIn("age", 21, 12, 190);
        /*
        ==>  Preparing: SELECT id,name,age,email,create_time,update_time,version,is_deleted FROM mp_user WHERE is_deleted=0 AND (age = ? AND name LIKE ? OR name LIKE ? OR name LIKE ?)
        ==> Parameters: 21(Integer), %i%(String), %i(String), i%(String)
        <==      Total: 4
        */
		List<MybatisPlusUser> selectList = mybatisPlusUserMapper.selectList(queryWrapper);
		selectList.forEach(user -> log.info("{}", user));
	}

	/**
	 * 条件构造器queryWrapper2
	 */
	@Test
	public void test16() {
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);
        /*
        ==>  Preparing: SELECT COUNT( * ) AS total FROM mp_user WHERE is_deleted=0 AND (age BETWEEN ? AND ?)
        ==> Parameters: 12(Integer), 30(Integer)
        <==      Total: 1
        */
		Long selectCount = mybatisPlusUserMapper.selectCount(queryWrapper);
		log.info("{}", selectCount);
	}

	/**
	 * 条件构造器queryWrapper3
	 */
	@Test
	public void test17() {
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		//排序
		queryWrapper.orderByAsc("age");
//		queryWrapper.orderByDesc("age");
        /*
        ==>  Preparing: SELECT id,name,age,email,create_time,update_time,version,is_deleted FROM mp_user WHERE is_deleted=0 AND (age BETWEEN ? AND ?) ORDER BY age ASC
        ==> Parameters: 12(Integer), 30(Integer)
        <==      Total: 9
        */
		List<MybatisPlusUser> selectList = mybatisPlusUserMapper.selectList(queryWrapper);//WHERE is_deleted=0 AND (age BETWEEN ? AND ?) ORDER BY age ASC
		selectList.forEach(user -> log.info("{}", user));
	}

	/**
	 * last追加自定义SQL，手动分页
	 */
	@Test
	public void test18() {
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		queryWrapper.last("limit 1");//只返回一条数据
		queryWrapper.last("limit 2");//并且只能调用一次，调用多次只保留最后一次
		List<MybatisPlusUser> selectList = mybatisPlusUserMapper.selectList(queryWrapper);//WHERE is_deleted=0 AND (age BETWEEN ? AND ?) limit 2
        /*
        ==>  Preparing: SELECT id,name,age,email,create_time,update_time,version,is_deleted FROM mp_user WHERE is_deleted=0 AND (age BETWEEN ? AND ?) limit 2
        ==> Parameters: 12(Integer), 30(Integer)
        <==      Total: 2
        */
		selectList.forEach(user -> log.info("{}", user));
	}

	/**
	 * last存在SQL注入风险
	 */
	@Test
	public void test19() {
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		queryWrapper.last("or 1 = 1");//慎用，存在SQL注入的风险，所有条件都成立	WHERE is_deleted=0 AND (age BETWEEN ? AND ?) or 1 = 1
		List<MybatisPlusUser> selectList = mybatisPlusUserMapper.selectList(queryWrapper);//WHERE is_deleted=0 AND (age BETWEEN ? AND ?) limit 2
		selectList.forEach(user -> log.info("{}", user));
	}

	/**
	 * 指定查询列
	 */
	@Test
	public void test20() {
		//默认查询所有的列，工作室需要什么查询什么
		//SELECT id,name,age,email,create_time,update_time,version,is_deleted FROM user WHERE is_deleted=0 AND (age BETWEEN ? AND ?) or 1 = 1
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		queryWrapper.select("id", "name", "age");//无位置要求
        /*
        ==>  Preparing: SELECT id,name,age FROM mp_user WHERE is_deleted=0 AND (age BETWEEN ? AND ?)
        ==> Parameters: 12(Integer), 30(Integer)
        <==      Total: 9
        */
		List<MybatisPlusUser> selectList = mybatisPlusUserMapper.selectList(queryWrapper);//WHERE is_deleted=0 AND (age BETWEEN ? AND ?) limit 2
		selectList.forEach(user -> log.info("{}", user));
	}

	/**
	 * 条件删除
	 */
	@Test
	public void test21() {
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);
		mybatisPlusUserMapper.delete(queryWrapper);
	}

	/**
	 * 条件修改
	 */
	@Test
	public void test22() {
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		MybatisPlusUser mybatisPlusUser = new MybatisPlusUser();
		mybatisPlusUser.setAge(12);
		mybatisPlusUserMapper.update(mybatisPlusUser, queryWrapper);
	}

	/**
	 * TooManyResultsException
	 * mapper.selectOne()
	 */
	@Test
	public void test23() {
		QueryWrapper<MybatisPlusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 12, 30);

		//结果太多异常 TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 12
//        mybatisPlusUserMapper.selectOne(queryWrapper);// 查到的数据最多就一行
	}

	/**
	 * 自定义xml文件写复杂的SQL语句
	 */
	@GetMapping("/selectAll")
	public ResultResponse selectAll() {
        /*
        ==>  Preparing: select * from mp_user
        ==> Parameters:
        <==      Total: 16
        */
		return ResultResponse.success(mybatisPlusUserService.selectAll());
	}

	@Test
	void lambda1() {
		// Invalid bound statement (not found): com.xjdl.study.myBatisPlus.MybatisPlusUserService.getBaseMapper 已解决
		// 将Service包和Mapper包进行区分，两者同时扫描到了mapper.xml文件，只有Mapper生效了，Service无法调用
		MybatisPlusUser mybatisPlusUser = mybatisPlusUserService.lambdaQuery().eq(MybatisPlusUser::getId, 18L).one();
		log.info("{}", mybatisPlusUser);

		mybatisPlusUserService.lambdaUpdate().eq(MybatisPlusUser::getId, 18L).set(MybatisPlusUser::getName, "Hello World");

		MybatisPlusUser mybatisPlusUserAfter = mybatisPlusUserService.lambdaQuery().eq(MybatisPlusUser::getName, "Hello World").one();
		log.info("{}", mybatisPlusUserAfter);
	}

	@Test
	void lambda2() {
		LambdaQueryWrapper<MybatisPlusUser> mybatisPlusUserLambdaQueryWrapper = Wrappers.lambdaQuery(MybatisPlusUser.class);
		mybatisPlusUserLambdaQueryWrapper.eq(MybatisPlusUser::getId, 18L);
		MybatisPlusUser mybatisPlusUser = mybatisPlusUserMapper.selectOne(mybatisPlusUserLambdaQueryWrapper);
		log.info("{}", mybatisPlusUser);

		LambdaUpdateWrapper<MybatisPlusUser> mybatisPlusUserLambdaUpdateWrapper = Wrappers.lambdaUpdate(MybatisPlusUser.class);
		mybatisPlusUserLambdaUpdateWrapper.eq(MybatisPlusUser::getId, 18L).set(MybatisPlusUser::getName, "Hello World");
		mybatisPlusUserMapper.update(new MybatisPlusUser(), mybatisPlusUserLambdaUpdateWrapper);

		LambdaQueryWrapper<MybatisPlusUser> mybatisPlusUserLambdaQueryWrapperAfter = Wrappers.lambdaQuery(MybatisPlusUser.class);
		mybatisPlusUserLambdaQueryWrapperAfter.eq(MybatisPlusUser::getName, "Hello World");
		MybatisPlusUser mybatisPlusUserAfter = mybatisPlusUserMapper.selectOne(mybatisPlusUserLambdaQueryWrapperAfter);
		log.info("{}", mybatisPlusUserAfter);
	}
}
