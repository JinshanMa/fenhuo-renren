package io.renren.modules.fenhuo.utils;


import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class OpUtils {

    private static String absolutePath = "";

    private static String staticDir = "static";

    private static String fileDir = "/fileupload/";

    public static  String upload(InputStream inputStream,String path,String filename){
        //第一次会创建文件夹
        createDirIfNotExists();

        String resultPath = fileDir + path + filename;

        //存文件
        File uploadFile = new File(absolutePath, staticDir + resultPath);
        try {
            FileUtils.copyInputStreamToFile(inputStream, uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return resultPath;
    }

    /**
     * 创建文件夹路径
     */
    private static void createDirIfNotExists() {
        if (!absolutePath.isEmpty()) {return;}

        //获取跟目录
        File file = null;
        try {
            file = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取根目录失败，无法创建上传目录！");
        }
        if(!file.exists()) {
            file = new File("");
        }

        absolutePath = file.getAbsolutePath();

        File upload = new File(absolutePath, staticDir + fileDir);
        if(!upload.exists()) {
            upload.mkdirs();
        }
    }


    /**
     * 删除文件
     * @param path 文件访问的路径upload开始 如： /upload/image/test.jpg
     * @return true 删除成功； false 删除失败
     */
    public static boolean delete(String path) {
        File file = new File(absolutePath, staticDir + path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }



    public String getPath()
    {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        //System.out.println("upload path is:" + path);
        if(System.getProperty("os.name").contains("dows"))
        {
            System.out.println("before substring;" + path);
            path = path.substring(1);
            System.out.println("after substring;" + path);
        }
//        else if (System.getProperty("os.name").contains("nux")){
//            path = path + "/";
//        }
        if(path.contains("jar"))
        {
            path = path.substring(0,path.lastIndexOf("."));
            return path.substring(0,path.lastIndexOf("/"));
        }
//        System.out.println("upload path is:" + path);
        return path.replace("target/classes/", "");
    }


    public static EPlatform osName(){
        String osname = System.getProperty("os.name").toLowerCase();
        if (osname.contains("windows")){
            return EPlatform.Windows;
        } else if(osname.contains("linux")){
            return EPlatform.Linux;
        }
        return EPlatform.Nnknow;
    }

    public static String getSplitNotation(){
        if (osName().equals(EPlatform.Linux)){
            return ":";
        }else if(osName().equals(EPlatform.Windows)){
            return ";";
        }
        return ";";
    }
    public static String getBacklash(){
        if (osName().equals(EPlatform.Linux)){
            return "/";
        }else if(osName().equals(EPlatform.Windows)){
            return "\\";
        }
        return "\\";
    }


    public static void main(String[] args) {
        //System.out.println(osName());

    }

}
