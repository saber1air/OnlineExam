package exam.dao.base;

import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import exam.model.page.PageBean;

public interface BaseDao<T> {

	/**
	 * 换种更新方式，是不是更好?
	 * 这样可以解决学生、老师修改时密码和姓名不同时修改的问题
	 */
	void executeSql(String sql, Object[] params);
	
	List<T> find(T entity);
	
	/**
	 * 执行一条sql语句
	 */
	void executeSql(String sql);
	
	/**
	 * 根据sql查询
	 */
	List<T> queryBySQL(String sql);
	
	List<T> queryBySQL(String sql, Object...params);
	
	/**
	 * 单值查询
	 */
	Object queryForObject(String sql, Class<?> clazz);
	
	/**
	 * 批量更新，其实直使用存储过程貌似是一个better的方式...
	 * @param sqls sql数组
	 * @return 我也不知道是什么，看文档去...
	 */
	public int[] batchUpdate(String...sqls);
	
	
	/**
     * 执行sql语句并且返回生成的主键id
     * @param sql
     * @param callback createPreparedStatement方法需要返回一个PreparedStatement，而PreparedStatement需要设置参数
     * @param param sql的?由param内的属性替换
     * @return 主键id
     */
    int getKeyHelper(final String sql, final GenerateKeyCallback callback, final Object param);
    
	
	/**
	 * 获取用于统计记录数量的sql语句
	 */
	String getCountSql();

	/**
	 * 获取用于查询此实体类的sql
	 */
	String getSql();

	/**
	 * 获取特定与某一个实体的mapper
	 */
	RowMapper<T> getRowMapper();
	
	/**
	 * 分页查询
	 * @param pageCode 需要查询的页码
	 * @param pageSize 每页的大小
	 * @param pageNumber 显示的页码数量
	 * @param where where条件语句
	 * @param params 参数列表
	 * @param orderbys 排序条件，比如id desc
	 * @return {@link PageBean}
	 */
	PageBean<T> pageSearch(int pageCode, int pageSize, int pageNumber, String where,
			List<Object> params, HashMap<String, String> orderbys);

}
