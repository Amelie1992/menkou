package com.wzlue.common.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;
import com.wzlue.common.utils.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 *  存储到数据库, 将JSON对象转换成字符串;
 *  从数据库获取数据, 将字符串转为JSON对象.
 */
@MappedTypes({JSONObject.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class JsonTypeHandler extends BaseTypeHandler<JSONObject> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter,
									JdbcType jdbcType) throws SQLException {

		ps.setString(i, JsonUtil.getBeanToJson(parameter));
	}

	@Override
	public JSONObject getNullableResult(ResultSet rs, String columnName)
			throws SQLException {

		return JsonUtil.getJsonToBean(rs.getString(columnName), JSONObject.class);
	}

	@Override
	public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

		return JsonUtil.getJsonToBean(rs.getString(columnIndex), JSONObject.class);
	}

	@Override
	public JSONObject getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {

		return JsonUtil.getJsonToBean(cs.getString(columnIndex), JSONObject.class);
	}

}
