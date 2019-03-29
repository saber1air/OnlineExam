package exam.controller.teacher;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by pdl on 2019/3/28.
 */
@RequestMapping("/teacherupload")
public class UploadController {

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public void uploadImage(@RequestParam(value = "file") MultipartFile file, Model model) throws RuntimeException {
        if (file.isEmpty()) {
            model.addAttribute("success", false);
            return;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "./images/questionimg/";
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
            model.addAttribute("success", true);
            model.addAttribute("filePath", "images/questionimg/"+newFileName);
            return;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

}
