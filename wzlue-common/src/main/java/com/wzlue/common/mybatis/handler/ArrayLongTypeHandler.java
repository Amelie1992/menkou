package com.wzlue.common.mybatis.handler;

import com.wzlue.common.utils.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  存储到数据库, 将LONG数组转换成字符串;
 *  从数据库获取数据, 将字符串转为LONG数组.
 */
@MappedTypes({Long[].class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class ArrayLongTypeHandler extends BaseTypeHandler<Long[]> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
									Long[] parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, JsonUtil.getBeanToJson(parameter));
	}

	@Override
	public Long[] getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return JsonUtil.getJsonToBean(rs.getString(columnName),Long[].class);
	}

	@Override
	public Long[] getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return JsonUtil.getJsonToBean(rs.getString(columnIndex),Long[].class);
	}

	@Override
	public Long[] getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return JsonUtil.getJsonToBean(cs.getString(columnIndex),Long[].class);
	}

}
