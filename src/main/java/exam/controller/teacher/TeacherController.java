package exam.controller.teacher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sun.org.apache.xpath.internal.operations.Mod;
import exam.dto.ClassDTO;
import exam.model.Grade;
import exam.model.role.Teacher;
import exam.service.GradeService;
import exam.service.TeacherService;
import exam.util.DataUtil;
import exam.util.ResultInfo;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 教师部分
 * @author skywalker
 *
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private GradeService gradeService;

    @Value("#{properties['cbs.imagesPath']}")
    private String mImagesPath;
	
	/**
	 * 转到教师模块主页
	 */
	@RequestMapping("/index")
	public String index() {
		return "teacher/index";
	}

    /**
     * 转向修改密码
     */
    @RequestMapping("/password")
    public String password() {
        return "teacher/password";
    }

    /**
     * 校验旧密码
     * @param password 旧密码
     */
    @RequestMapping("/password/check")
    @ResponseBody
    public void check(String password, HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        if (teacher.getPassword().equals(password)) {
            json.addElement("result", "1");
        } else {
            json.addElement("result", "0");
        }
        DataUtil.writeJSON(json, response);
    }
    
    /**
     * 获得此教师所教的班级(含专业、年级信息)
     * @param session
     * @param response
     */
    @RequestMapping("/classes")
    @ResponseBody
    public void classes(HttpSession session, HttpServletResponse response) {
    	JSONObject json = new JSONObject();
    	Teacher teacher = (Teacher) session.getAttribute("teacher");
    	//List<ClassDTO> dtoes = teacherService.getClassesWithMajorAndGrade(teacher.getId());
        //List<Grade> dtoes = gradeService.getGrade();
        List<ClassDTO> dtoes = new ArrayList<>();
        ClassDTO cdto = new ClassDTO();
        cdto.setCid(1);
        cdto.setCno(2);
        cdto.setGid(1);
        cdto.setGrade(2012);
        cdto.setMajor("电子信息科学与技术");
        cdto.setMid(62);
        dtoes.add(cdto);
    	JSONArray array = new JSONArray();
        /*for (Grade dto : dtoes) {
            array.addObject(dto.getJSON());
        }*/
    	for (ClassDTO dto : dtoes) {
    		array.addObject(dto.getJSON());
    	}
    	json.addElement("result", "1").addElement("data", array);
    	DataUtil.writeJSON(json, response);
    }

    @RequestMapping("/password/modify")
    public String modifyPassword(String oldPassword, String newPassword, HttpServletRequest request, Model model) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        if (!checkPassword(oldPassword, newPassword, teacher)) {
            return "error";
        }
        teacherService.updatePassword(teacher.getId(), newPassword);
        teacher.setPassword(newPassword);
        teacher.setModified(true);
        model.addAttribute("message", "密码修改成功");
        model.addAttribute("url", request.getContextPath() + "/teacher/index");
        return "success";
    }

    @RequestMapping(value = "/uploadImage")
    @ResponseBody
    public void uploadImage(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response) throws RuntimeException {
        JSONObject json = new JSONObject();
        if (file.isEmpty()) {
            return;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = mImagesPath;
        // 解决中文问题，liunx下中文路径，图片显示问题
        String newFileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + newFileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            //logger.info("上传成功后的文件路径未：" + filePath + fileName);

            json.addElement("result", "1").addElement("filePath", "examImages/"+newFileName);
            DataUtil.writeJSON(json, response);
            return;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    /**
     * 检查旧密码和新密码
     * @param oldPassword 必须和session里面保存的密码一致
     * @param newPassword 必须是4-10，由数字、字母、下划线组成
     * @param teacher
     * @return 通过返回true
     */
    private boolean checkPassword(String oldPassword, String newPassword, Teacher teacher) {
        if (!teacher.getPassword().equals(oldPassword)) {
            return false;
        }
        if (!DataUtil.isValid(newPassword) || !newPassword.matches("^\\w{4,10}$")) {
            return false;
        }
        return true;
    }
	
}
