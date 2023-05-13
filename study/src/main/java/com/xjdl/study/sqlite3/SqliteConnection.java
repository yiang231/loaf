package com.xjdl.study.sqlite3;

import com.xjdl.study.jpa.JpaUser;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public class SqliteConnection {
	public static void main(String[] args) {
		try (
				Connection connection = DriverManager.getConnection("jdbc:sqlite::resource:db.sqlite");
				Statement statement = connection.createStatement();
		) {
			Class.forName("org.sqlite.JDBC");

			connection.setAutoCommit(false);
			log.info("Opened database successfully");

			ResultSet resultSet = statement.executeQuery("select * from jpa_user;");
			JpaUser jpaUser = new JpaUser();
			while (resultSet.next()) {
				jpaUser.setId(resultSet.getLong("id"));
				jpaUser.setName(resultSet.getString("name"));
				log.info("{}", jpaUser);
			}
			log.info("Operation done successfully");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}

