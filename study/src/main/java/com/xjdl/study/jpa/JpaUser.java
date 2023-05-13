package com.xjdl.study.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity // 告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "jpa_user") // @Table来指定和哪个数据表对应;如果省略默认表名就是user
public class JpaUser implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", length = 50, nullable = false) //这是和数据表对应的一个列，省略默认列名就是属性名
	private String name;
	// 时间精度
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	// JPA会忽略该属性，不会映射到数据库中，即程序运行后数据库中将不会有该字段。
	@Transient
	private String deleted;
}
