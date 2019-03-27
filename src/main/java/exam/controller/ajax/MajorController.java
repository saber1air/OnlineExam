package exam.controller.ajax;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import exam.model.Major;
import exam.service.MajorService;
import exam.util.DataUtil;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;

/**
 * 获取专业信息
 * @author skywalker
 *
 */
@Controller
@RequestMapping("/major")
public class MajorController {

	@Resource
	private MajorService majorService;
	
	/**
	 * 利用ajax根据年级获取专业
	 */
	@RequestMapping("/ajax")
	@ResponseBody
	public void ajax(String grade, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		List<Major> majors = null;;
		if (!DataUtil.isValid(grade)) {
			majors = majorService.findAll();
		} else if (!DataUtil.isNumber(grade)) {
			json.addElement("result", "0").addElement("message", "年级格式非法");
		} else {
			majors = majorService.findByGrade(Integer.parseInt(grade));
		}
		if (majors != null) {
			json.addElement("result", "1");
			JSONArray array = new JSONArray();
			for (Major major : majors) {
				array.addObject(major.getJSON());
			}
			json.addElement("data", array);
		}
		DataUtil.writeJSON(json, response);
	}
	
}
