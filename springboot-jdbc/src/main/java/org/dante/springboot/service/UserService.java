package org.dante.springboot.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import org.dante.springboot.po.ContactPO;
import org.dante.springboot.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insert(UserPO user) {
		jdbcTemplate.update("insert into t_user(name, age) values (?, ?)", user.getName(), user.getAge());
	}
	
	public UserPO insertReturnId(UserPO user) {
		final String sql = "insert into t_user(name, age) values (?, ?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setInt(2, user.getAge());
				return ps;
			}
		}, holder);
		Long newUserId = holder.getKey().longValue();
        user.setId(newUserId);
        return user;
	}
	
	public void batchInsert(List<UserPO> users) {
		final String sql = "insert into t_user(name, age) values (?, ?)";
		jdbcTemplate.batchUpdate(sql, users.stream()
											.map(u -> new Object[]{u.getName(), u.getAge()})
											.collect(Collectors.toList()));
	}
	
	public void update(UserPO user) {
		final String sql = "update t_user set age = ? where id = ?";
		jdbcTemplate.update(sql, user.getAge(), user.getId());
	}
	
	public void deleteById(Long id) {
		final String sql = "delete from t_user where id = ?";
		jdbcTemplate.update(sql, id);
	}
	
	/**
	 * 查询
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<UserPO> queryUsers() {
		return jdbcTemplate.query("select t.id, t.name, t.age, t1.id as contactId, t1.user_id, t1.mobile, t1.address from t_user t left join t_contact t1 on t.id = t1.user_id order by t.age desc", new Object[]{}, new RowMapper<UserPO>(){
			@Override
			public UserPO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserPO user = new UserPO();
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setAge(rs.getInt("age"));
				ContactPO contact = new ContactPO();
				contact.setId(rs.getLong("contactId"));
				contact.setUserId(rs.getLong("user_id"));
				contact.setMobile(rs.getString("mobile"));
				contact.setAddress(rs.getString("address"));
				user.getContacts().add(contact);
				return user;
			}
		});
	}
	
}
