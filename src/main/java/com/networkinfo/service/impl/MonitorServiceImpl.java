package com.networkinfo.service.impl;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import com.networkinfo.bean.MonitorInfoBean;
import com.networkinfo.service.IMonitorService;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MonitorServiceImpl implements IMonitorService {


    public MonitorInfoBean getMonitorInfoBean() throws Exception {
        int kb = 1024;

        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();

        // 操作系统
        String osName = System.getProperty("os.name");
        // 总的物理内存
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb/kb;
        // 已使用的物理内存
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
                .getFreePhysicalMemorySize())
                / kb/kb;
        // 构造返回对象
        MonitorInfoBean infoBean = new MonitorInfoBean();
        infoBean.setOsName(osName);
        infoBean.setTotalMemorySize(totalMemorySize);
        infoBean.setUsedMemory(usedMemory);
        return infoBean;
    }

    public Map<String,Object> getSystemInfo() throws Exception {
        IMonitorService service = new MonitorServiceImpl();
        MonitorInfoBean monitorInfo = service.getMonitorInfoBean();
        double rate = ((double)monitorInfo.getUsedMemory()/(double)monitorInfo.getTotalMemorySize())*100;
        System.out.println("操作系统=" + monitorInfo.getOsName());
        System.out.println("总的物理内存=" + monitorInfo.getTotalMemorySize() + "mb");
        System.out.println("已使用的物理内存=" + monitorInfo.getUsedMemory() + "mb");
        System.out.println((int)rate);
        Map<String,Object> res = new HashMap<>();
        res.put("system",monitorInfo.getOsName());
        res.put("rate",(int)rate);
        return res;
    }
}
