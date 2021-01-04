package com.networkinfo.bean;

public class MonitorInfoBean {

    /** 操作系统. */
    private String osName;

    /** 总的物理内存. */
    private long totalMemorySize;


    /** 已使用的物理内存. */
    private long usedMemory;


    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }


    public long getTotalMemorySize() {
        return totalMemorySize;
    }

    public void setTotalMemorySize(long totalMemorySize) {
        this.totalMemorySize = totalMemorySize;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

}