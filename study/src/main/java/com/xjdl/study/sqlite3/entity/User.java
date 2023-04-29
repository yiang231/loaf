package com.xjdl.study.sqlite3.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity // 告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "users") // @Table来指定和哪个数据表对应;如果省略默认表名就是user
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name",length = 50) //这是和数据表对应的一个列，省略默认列名就是属性名
	private String name;
}
