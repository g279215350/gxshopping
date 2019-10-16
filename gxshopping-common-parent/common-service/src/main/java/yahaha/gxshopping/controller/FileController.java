package yahaha.gxshopping.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import yahaha.gxshopping.util.AjaxResult;
import yahaha.gxshopping.util.FastDfsApiOpr;

import java.io.IOException;

@RestController
public class FileController {

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/file")
    public AjaxResult upLoad(MultipartFile file){
        try {
            String filename = file.getOriginalFilename();
            int index = filename.lastIndexOf(".");
            String extName = filename.substring(index + 1);

            byte[] content = file.getBytes();

            String file_id = FastDfsApiOpr.upload(content, extName);

            return AjaxResult.me().setMessage("上传成功！").setRestObj(file_id);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上传失败！" + e.getMessage());
        }
    }

    @DeleteMapping("/file")
    public AjaxResult delete(String fileId){

        try {
            //先删除第一个/
            fileId = fileId.substring(1);
            //获取到组名后的/索引
            int index = fileId.indexOf("/");
            //获取到组名
            String group = fileId.substring(0, index);
            //获取文件路径
            String filePath = fileId.substring(index + 1);
            FastDfsApiOpr.delete(group, filePath);
            return AjaxResult.me().setMessage("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败！"+e.getMessage());
        }
    }
}
