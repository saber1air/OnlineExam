package exam.service;

import exam.model.Grade;
import exam.service.base.BaseService;

import java.util.List;

public interface GradeService extends BaseService<Grade> {

	/**
	 * 根据年级搜索
	 */
	public Grade findByGrade(String grade);

	public List<Grade> getGrade();
	
}
