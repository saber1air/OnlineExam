package exam.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.dao.MajorDao;
import exam.dao.base.BaseDaoImpl;
import exam.model.Major;
import exam.util.DataUtil;

@Repository("majorDao")
public class MajorDaoImpl extends BaseDaoImpl<Major> implements MajorDao {
	
	private static RowMapper<Major> rowMapper;
	private static String sql = "select * from major";
	
	static {
		rowMapper = new RowMapper<Major>() {
			public Major mapRow(ResultSet rs, int rowNum) throws SQLException {
				Major major = new Major();
				major.setId(rs.getInt("id"));
				major.setName(rs.getString("name"));
				return major;
			}
		};
	}
	
	@Override
	public List<Major> find(Major enity) {
		StringBuilder sqlBuilder = new StringBuilder(sql).append(" where 1 = 1");
		if(enity != null) {
			if(enity.getId() > 0) {
				sqlBuilder.append(" and id = ").append(enity.getId());
			}
			if(DataUtil.isValid(enity.getName())) {
				sqlBuilder.append(" and name = '").append(enity.getName()).append("'");
			}
		}
		return jdbcTemplate.query(sqlBuilder.toString(), rowMapper);
	}


	public RowMapper<Major> getRowMapper() {
		return rowMapper;
	}

	public String getSql() {
		return sql;
	}

	public String getCountSql() {
		return "select count(id) from major";
	}

}
