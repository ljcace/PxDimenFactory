package com.ljc.pxdimenbuilder.utils;

import com.ljc.pxdimenbuilder.beans.WHBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * Created by lijiacheng on 2017/11/29.
 */

public class FileUtil {
    private static final String PROJECT_HOME = System.getProperty("user.dir");//系统工程名
    private static final String PATH = PROJECT_HOME + File.separator + "app" + File.separator + "src" + File.separator + "main" + File.separator + "res" + File.separator;
    private static String fileName = "dimen.xml";

    public static void createFile(String mkdirName, WHBean fValue, WHBean tValue, int max, int newScale) {
        File file = new File(PATH + mkdirName);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(PATH + mkdirName + File.separator + fileName);
        if (!file.exists()) {
            FileInputStream fis = null;
            InputStreamReader isr = null;

            FileOutputStream fos = null;
            PrintWriter pw = null;
            try {
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                        fis = new FileInputStream(file);
                        isr = new InputStreamReader(fis);

                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<resources>\n");
                        float p = tValue.getWidth() * 1.0f / fValue.getWidth();
                        for (int i = 0; i <= max; i++) {
                            BigDecimal b = new BigDecimal(i * p);
                            float temp = b.setScale(newScale, BigDecimal.ROUND_HALF_UP).floatValue();
                            stringBuffer.append("<dimen name=\"dim" + i + "\">" + temp + "px</dimen>\n");
                        }
                        stringBuffer.append("</resources>");
                        System.out.println(stringBuffer.toString());
                        fos = new FileOutputStream(file);
                        pw = new PrintWriter(fos);
                        pw.write(stringBuffer.toString().toCharArray());
                        pw.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (fis != null) {
                            fis.close();
                        }
                        if (isr != null) {
                            isr.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                        if (pw != null) {
                            pw.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.delete();
            createFile(mkdirName, fValue, tValue, max, newScale);
        }
    }
}
