package org.dante.springboot.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import cn.hutool.crypto.SmUtil;

@MappedTypes(CipherEncrypt.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class CipherTypeHandler extends BaseTypeHandler<CipherEncrypt> {

	/**
	 * 设置参数
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, CipherEncrypt parameter, JdbcType jdbcType)
			throws SQLException {
		if (parameter == null || parameter.getValue() == null) {
            ps.setString(i, null);
            return;
        }
		ps.setString(i, SmUtil.sm4().encryptHex(parameter.getValue()));
	}

	/**
	 * 获取值
	 */
	@Override
	public CipherEncrypt getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return new CipherEncrypt(rs.getString(columnName));
	}

	/**
	 * 获取值
	 */
	@Override
	public CipherEncrypt getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return new CipherEncrypt(rs.getString(columnIndex));
	}

	/**
	 * 获取值
	 */
	@Override
	public CipherEncrypt getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return new CipherEncrypt(cs.getString(columnIndex));
	}

}
