package com.self.common.old;

import org.springframework.mobile.device.Device;

/**
 * @author dailong
 * @ClassName: DeviceUtil
 * @Description: TODO
 * @date 2018/12/27 上午11:10
 */
public class DeviceUtil {

    public static String deviceDetect(Device device){
        String deviceType="unknown";
        if(device.isNormal()){
            deviceType = "normal";//Pc端
        }else if(device.isMobile()){
            deviceType = "mobile";//手机端
        }else if(device.isTablet()){
            deviceType = "tablet";//平板
        }
        return deviceType;
    }
}
