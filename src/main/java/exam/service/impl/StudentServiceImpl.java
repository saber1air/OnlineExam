package exam.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import exam.dao.StudentDao;
import exam.dao.base.BaseDao;
import exam.model.role.Student;
import exam.service.StudentService;
import exam.service.base.BaseServiceImpl;
import exam.util.DataUtil;
import exam.util.StringUtil;

@Service("studentService")
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
	
	private StudentDao studentDao;

	@Resource(name = "studentDao")
	@Override
	protected void setBaseDao(BaseDao<Student> baseDao) {
		super.baseDao = baseDao;
		this.studentDao = (StudentDao) baseDao;
	}
	
	@Override
	public boolean isExisted(String id) {
		BigInteger result = (BigInteger) studentDao.queryForObject("select count(id) from student where id = " + id, BigInteger.class);
		return result.intValue() > 0;
	}
	
	@Override
	public void updatePassword(String id, String password) {
		String sql = "update student set password = ?, modified = 1 where id = ?";
		studentDao.executeSql(sql, new Object[]{StringUtil.md5(password), id});
	}



	@Override
	public void saveStudent(String id, String name, String password, int cid) {
		studentDao.executeSql("insert into student values(?, ?, ?, ?, 0)",
				new Object[] {id, name, password, cid});
	}
	
	@Override
	public void updateStudent(int cid, String name, String id) {
		String sql = "update student set name = ?, cid = ? where id = ?";
		studentDao.executeSql(sql, new Object[]{name, cid, id});
	}
	
	@Override
	public void delete(Object id) {
		//删除学生需要删除: 学生 -> 学生的考试记录(examinationresult) -> 做题记录(examinationresult_question)
		String[] sqls = {
			//删除做题记录
			"delete from examinationresult_question where erid in (select id from examinationresult where sid = '" + id +  "')",
			//删除考试记录
			"delete from examinationresult where sid = '" + id + "'",
			//删除学生
			"delete from student where id = '" + id + "'"
		};
		studentDao.batchUpdate(sqls);
	}
	


	@Override
	public Student loginStudent(String username, String password) {
		/*String sql = "select s.id as s_id, s.name as s_name, s.password as s_password, s.modified as s_modified " +
				" from student s where s.name = ? and s.password = ?";
		List<Student> result = studentDao.queryBySQL(sql, username, StringUtil.md5(password));
		System.out.println("测试42:"+sql);
		return DataUtil.isValid(result) ? result.get(0) : null;*/

		String sql = "select s.id as s_id, s.name as s_name, s.password as s_password, " +
				" s.modified as s_modified from student s where s.name = ? and s.password = ?";
		List<Student> result = studentDao.queryBySQL(sql, username, StringUtil.md5(password));
		return DataUtil.isValid(result) ? result.get(0) : null;

		/*String sql = studentDao.getSql() + " where s.name = '" + username + "' and s.password = '" + StringUtil.md5(password) + "'";
		System.out.println("测试42:"+sql);
		List<Map<String,Object>> result = studentDao.queryBySQL(sql);
		return DataUtil.isValid(result) ? result.get(0) : null;*/
	}
	
}
