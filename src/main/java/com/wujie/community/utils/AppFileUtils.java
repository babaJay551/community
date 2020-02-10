package com.wujie.community.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;

/**
 * 文件上传下载工具类
 *
 */
public class AppFileUtils {
    //文件上传的保存路径
    public static String UPLOAD_PATH = "E:/upload/";//默认值

    static {
        //读取配置文件的存储地址
       /* InputStream stream = AppFileUtils.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String property = "E:/upload/";
        if (null != property) {
            UPLOAD_PATH = property;
        }

    }

    /**
     * 根据文件老名字得到新名字
     *
     * @param oldName
     * @return
     */
    public static String createNewFileName(String oldName) {

        String stuff = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        return IdUtil.simpleUUID().toUpperCase() + stuff;
    }

    /**
     * 文件下载
     *
     * @param path
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path) {
        //1,构造文件对象
        File file = new File(UPLOAD_PATH, path);
        if (file.exists()) {
            //将下载文件，封装到btye【】数组中
            byte[] bytes = null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //创建封装响应头信息的对象
            HttpHeaders header = new HttpHeaders();
            //封装响应内容类型(APPLICATION_OCTET_STREAM 响应的内容不限定)
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置下载的文件的名称
//			header.setContentDispositionFormData("attachment", "123.jpg");
            //创建ResponseEntity对象
            ResponseEntity<Object> entity = new ResponseEntity<>(bytes, header, HttpStatus.CREATED);
            return entity;
        }

        return null;
    }

    /**
     * 去掉  _temp
     * @param goodsimg
     * @return
     */
    public static String rename(String goodsimg) {

        File file=new File(UPLOAD_PATH, goodsimg);
        String replace = goodsimg.replace("_temp", "");
        if (file.exists()){
            file.renameTo(new File(UPLOAD_PATH,replace));
        }
        return replace;
    }

    /**
     * 删除原来的照片
     * @param goodsimg
     */
    public static void removeFileByPath(String goodsimg) {

        if (!goodsimg.equals("images/timg.jpg")){
            File file=new File(UPLOAD_PATH, goodsimg);
            if (file.exists()){
                file.delete();
            }
        }
    }
}
