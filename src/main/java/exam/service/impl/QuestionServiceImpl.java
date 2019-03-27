package exam.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import exam.dao.QuestionDao;
import exam.dao.base.BaseDao;
import exam.model.Question;
import exam.model.QuestionType;
import exam.service.QuestionService;
import exam.service.base.BaseServiceImpl;

@Service("questionService")
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {
	
	private QuestionDao questionDao;

	@Resource(name = "questionDao")
	@Override
	protected void setBaseDao(BaseDao<Question> baseDao) {
		super.baseDao = baseDao;
		this.questionDao = (QuestionDao) baseDao;
	}

	@Override
	public List<Question> getSingles(String tid) {
		return getQuestionsByType(tid, QuestionType.SINGLE);
	}

	@Override
	public List<Question> getMultis(String tid) {
		return getQuestionsByType(tid, QuestionType.MULTI);
	}

	@Override
	public List<Question> getJudges(String tid) {
		return getQuestionsByType(tid, QuestionType.JUDGE);
	}
	
	/**
	 * 保存或者修改
	 * 此方法是update和save真正的实现，判断的依据是question的id是否大于0
	 * @param question 问题
	 */
	@Override
	public void saveOrUpdate(Question question) {
		if (question.getId() > 0) {
			String sql = "update question set title = ?, optionA = ?, optionB = ?, optionC = ?, optionD = ?" +
					", answer = ?, point = ? where id = ?";
			questionDao.executeSql(sql, new Object[] {question.getTitle(), question.getOptionA(), question.getOptionB(),
					question.getOptionC(), question.getOptionD(), question.getAnswer(), question.getPoint(), question.getId()});
		} else {
			String sql = "insert into question values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			questionDao.executeSql(sql, new Object[] {question.getTitle(), question.getOptionA(), question.getOptionB(),
					question.getOptionC(), question.getOptionD(), question.getPoint(), question.getType().name(),
					question.getAnswer(), question.getTeacher().getId()});
		}
	}
	
	@Override
	public boolean isUsedByExam(int id) {
		String sql = "select count(id) from exam_question where qid = " + id;
		BigInteger count = (BigInteger) questionDao.queryForObject(sql, BigInteger.class);
		return count.intValue() > 0;
	}
	
	@Override
	public void delete(Object id) {
		//在controller里面已经对有试卷引用做了检查，所以到达这里的问题一定没有被做过的，所以可以直接删除
		String sql = "delete from question where id = " + (Integer) id;
		questionDao.executeSql(sql);
	}
	
	@Override
	public List<Question> findByExam(int eid) {
		String sql = "select * from question where id in (select qid from exam_question where eid = " + eid + ")";
		return questionDao.queryBySQL(sql);
	}
	
	/**
	 * 内部使用，根据题目类型和教师获取所有题目
	 * @param tid 教师id
	 * @param type 题型
	 */
	private List<Question> getQuestionsByType(String tid, QuestionType type) {
		String sql = questionDao.getSql() + " where tid = '" + tid + "' and type = '" + type.name() + "'";
		return questionDao.queryBySQL(sql);
	}
	
	@Override
	public Double articulationScore(int qid) {
		//又遇到了一个坑，一开始数据库字段名是right，怎么搞都报错，原来right又是保留字，不特么说一声，日了狗了...
		String sql = "select sum(case when isright = 1 then 1 else 0 end) / count(id) from examinationresult_question where qid = "
				+ qid;
		Double result = (Double) questionDao.queryForObject(sql, Double.class);
		return result;
	}

}
