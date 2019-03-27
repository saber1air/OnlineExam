package exam.model;

import java.io.Serializable;

import exam.model.role.Teacher;
import exam.util.DataUtil;
import exam.util.json.JSON;
import exam.util.json.JSONAble;
import exam.util.json.JSONObject;

/**
 * 问题
 * @author skywalker
 *
 */
public class Question implements Serializable, JSONAble {

	private static final long serialVersionUID = 3817117285809180416L;
	private static String[] answerFacades = {"A", "B", "C", "D","E", "F", "G", "H"};
	private static String[] judgeAnserFacades = {"对", "错"};
	
	private int id;
	private String title;
	private String img;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String optionE;
	private String optionF;
	private String optionG;
	private String optionH;
	private String answer;
	//答案存储的是序号，比如1，但是显示在页面上的应该是字母的选项或是对错的形式
	private String answerFacade;
	private QuestionType type;
	private int point;
	private int pointA;
	private int pointB;
	private int pointC;
	private int pointD;
	private int pointE;
	private int pointF;
	private int pointG;
	private int pointH;
	private Teacher teacher;
	
	/**
	 * 返回此题的门面答案
	 */
	public String getAnswerFacade() {
		return this.answerFacade;
	}
	
	@Override
	public JSON getJSON() {
		JSONObject json = new JSONObject();
		json.addElement("id", String.valueOf(id)).addElement("title", title).addElement("img", img)
			.addElement("optionA", optionA)
			.addElement("optionB", optionB).addElement("optionC", optionC).addElement("optionD", optionD)
			.addElement("optionE", optionE).addElement("optionF", optionF).addElement("optionG", optionG)
			.addElement("optionH", optionH)
			.addElement("answer", answer).addElement("point", String.valueOf(point))
			.addElement("pointA", String.valueOf(pointA)).addElement("pointB", String.valueOf(pointB))
			.addElement("pointC", String.valueOf(pointC)).addElement("pointD", String.valueOf(pointD))
			.addElement("pointE", String.valueOf(pointE)).addElement("pointF", String.valueOf(pointF))
			.addElement("pointG", String.valueOf(pointG)).addElement("pointH", String.valueOf(pointH));
		return json;
	}


	@Override
	public String toString() {
		return "Question{" +
				"id=" + id +
				", title='" + title + '\'' +
				", img='" + img + '\'' +
				", optionA='" + optionA + '\'' +
				", optionB='" + optionB + '\'' +
				", optionC='" + optionC + '\'' +
				", optionD='" + optionD + '\'' +
				", optionE='" + optionE + '\'' +
				", optionF='" + optionF + '\'' +
				", optionG='" + optionG + '\'' +
				", optionH='" + optionH + '\'' +
				", answer='" + answer + '\'' +
				", answerFacade='" + answerFacade + '\'' +
				", type=" + type +
				", point=" + point +
				", pointA=" + pointA +
				", pointB=" + pointB +
				", pointC=" + pointC +
				", pointD=" + pointD +
				", pointE=" + pointE +
				", pointF=" + pointF +
				", pointG=" + pointG +
				", pointH=" + pointH +
				", teacher=" + teacher +
				'}';
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getAnswer() {
		return answer;
	}
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	public String getOptionE() {
		return optionE;
	}

	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}

	public String getOptionF() {
		return optionF;
	}

	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}

	public String getOptionG() {
		return optionG;
	}

	public void setOptionG(String optionG) {
		this.optionG = optionG;
	}

	public String getOptionH() {
		return optionH;
	}

	public void setOptionH(String optionH) {
		this.optionH = optionH;
	}

	public int getPointA() {
		return pointA;
	}

	public void setPointA(int pointA) {
		this.pointA = pointA;
	}

	public int getPointB() {
		return pointB;
	}

	public void setPointB(int pointB) {
		this.pointB = pointB;
	}

	public int getPointC() {
		return pointC;
	}

	public void setPointC(int pointC) {
		this.pointC = pointC;
	}

	public int getPointD() {
		return pointD;
	}

	public void setPointD(int pointD) {
		this.pointD = pointD;
	}

	public int getPointE() {
		return pointE;
	}

	public void setPointE(int pointE) {
		this.pointE = pointE;
	}

	public int getPointF() {
		return pointF;
	}

	public void setPointF(int pointF) {
		this.pointF = pointF;
	}

	public int getPointG() {
		return pointG;
	}

	public void setPointG(int pointG) {
		this.pointG = pointG;
	}

	public int getPointH() {
		return pointH;
	}

	public void setPointH(int pointH) {
		this.pointH = pointH;
	}

	protected String generateFacade(String answer) {
		String facade = "";
		//防止交白卷时报错
		if (DataUtil.isValid(answer)) {
			//设置其门面
			if (this.type == QuestionType.SINGLE) {
				facade = answerFacades[Integer.parseInt(answer)];
			} else if (this.type == QuestionType.MULTI) {
				String[] answers = answer.split(",");
				StringBuilder sb = new StringBuilder();
				for (String a : answers) {
					sb.append(answerFacades[Integer.parseInt(a)]).append(",");
				}
				facade = sb.deleteCharAt(sb.length() - 1).toString();
			} else {
				facade = judgeAnserFacades[Integer.parseInt(answer)];
			}
		}
		return facade;
	}
	
	/**
	 * 此方法需要先设置题型，这是一个隐藏的bug?
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
		this.answerFacade = generateFacade(answer);
		
	}
	public QuestionType getType() {
		return type;
	}
	public void setType(QuestionType type) {
		this.type = type;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
}
