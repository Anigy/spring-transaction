package com.angiy.spring.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


public class JDBCTest {

	private ApplicationContext ctx =  new ClassPathXmlApplicationContext("applicationContext.xml");
	private JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
	private EmployeeDao employeeDao = ctx.getBean(EmployeeDao.class);
	private DepartmentDao departmentDao = ctx.getBean(DepartmentDao.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}
	
	@Test
	public void testUpdate() {
		String sql = "UPDATE employees SET last_name = ? WHERE id = ?";
		jdbcTemplate.update(sql, "Anigy", 5);
	}
	
	@Test
	public void testBatchupdate() {
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(?,?,?)";
		List<Object[]> batchArgs = new ArrayList<>();
		batchArgs.add(new Object[] {"AA", "aa@163.com", 1});
		batchArgs.add(new Object[] {"BB", "bb@163.com", 2});
		batchArgs.add(new Object[] {"CC", "cc@163.com", 3});
		batchArgs.add(new Object[] {"DD", "dd@163.com", 3});
		batchArgs.add(new Object[] {"EE", "ee@163.com", 2});
		
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	@Test
	public void testQueryForObject() {
		String sql = "SELECT id, last_name lastName, email, dept_id as \"department.id\" FROM employees WHERE id = ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 1);
		System.out.println(employee);
	}
	
	@Test
	public void testQueryForList() {
		String sql = "SELECT id, last_name lastName, email FROM employees WHERE id > ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		List<Employee> employees = jdbcTemplate.query(sql, rowMapper, 5);
		
		System.out.println(employees);
	}
	
	@Test
	public void testQueryForStatic() {
		String sql = "SELECT count(id) FROM employees";
		long count = jdbcTemplate.queryForObject(sql, Long.class);
		
		System.out.println(count);
	}
	
	@Test
	public void testEmployeeDao() {
		System.out.println(employeeDao.get(1));
	}
	
	@Test
	public void testDepartmentDao() {
		System.out.println(departmentDao.get(1));
	}
	
	/**
	 * 使用具名参数时，可以为参数取名字
	 */
	@Test
	public void testNamedParameterJdbcTemplate() {
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUE(:ln, :email, :deptid)";
		
		java.util.Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ln", "FF");
		paramMap.put("email", "ff@163.com");
		paramMap.put("deptid", 2);
		
		namedParameterJdbcTemplate.update(sql, paramMap);		
	}
	
	@Test
	public void testNameParameterJdbcTemplate2() {
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUE(:lastName, :email, :deptId)";
		
		Employee employee = new Employee();
		employee.setLastName("XYZ");
		employee.setEmail("xyz@163.com");
		employee.setDeptId(3);
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);
		
		namedParameterJdbcTemplate.update(sql, paramSource);
	}

}
