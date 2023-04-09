package com.xjdl.study.sqlite3;

import com.xjdl.study.sqlite3.withJpa.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public class SqliteTest {
	public static void main(String[] args) {
		try (
				Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
				Statement statement = connection.createStatement();
		) {
			Class.forName("org.sqlite.JDBC");

			connection.setAutoCommit(false);
			log.info("Opened database successfully");

			ResultSet resultSet = statement.executeQuery("select * from users;");
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong("id"));
				user.setName(resultSet.getString("name"));
				log.info("{}", user);
			}
			log.info("Operation done successfully");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}

