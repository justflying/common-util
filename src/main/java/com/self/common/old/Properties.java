package com.self.common.old;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lizhong on 2017/12/19.
 */
public class Properties {

    private static String propertiesPath = String.format("%s/%s", new Object[]{System.getenv("btmspath"), "business_socket.properties"});

    private static java.util.Properties properties;
    //文件更新标识
    protected long lastModifiedData = -1;

    private static Properties instance;

    public static Properties getInstance(){
        if(instance == null){
            instance = new Properties();
        }
        return instance;
    }

    /**
     * @description 私有构造器启动一个线程实时监控配置文件
     */
    private Properties() {
        properties = new java.util.Properties();
        final Properties self = this;
        File propertyFile = new File(propertiesPath);
        if (propertyFile.exists()) reloadFile(propertyFile);

        //循环监控配置文件的变化，一旦发现文件发生变化了则重读配置文件
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                File propertyFile = new File(propertiesPath);
                if (self.lastModifiedData != propertyFile.lastModified()) {
                    self.reloadFile(propertyFile);
                    self.lastModifiedData = propertyFile.lastModified();
                }
            }
        };
        timer.schedule(task, new Date(), 1000);
    }

    /**
     * 加载文件
     * @author frank 2015-07-02
     * @param propertyFile
     */
    private void reloadFile(File propertyFile) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(propertyFile);
            properties.load(inputStream);
        } catch (Exception e) {
            properties = null;
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
