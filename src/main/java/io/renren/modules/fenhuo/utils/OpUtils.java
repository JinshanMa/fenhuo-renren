package io.renren.modules.fenhuo.utils;

public class OpUtils {



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
