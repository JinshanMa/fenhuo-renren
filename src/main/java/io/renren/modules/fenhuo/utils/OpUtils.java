package io.renren.modules.fenhuo.utils;

public class OpUtils {

    public String getPath()
    {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if(System.getProperty("os.name").contains("dows"))
        {
            System.out.println("before substring;" + path);
            path = path.substring(1);
            System.out.println("after substring;" + path);
        }
        if(path.contains("jar"))
        {
            path = path.substring(0,path.lastIndexOf("."));
            return path.substring(0,path.lastIndexOf("/"));
        }
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
        System.out.println(osName());
    }
}
