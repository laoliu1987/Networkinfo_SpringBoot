package com.networkinfo.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtils {
    public IPUtils(){

    }

    public Map<String,Object> getLocalInfo() {
        Map<String,Object> map = new HashMap<>();
        try {
            Process pro = Runtime.getRuntime()
                    .exec("cmd /c ipconfig /all");
            InputStreamReader isr = new InputStreamReader(pro.getInputStream(), "GBK");
            BufferedReader br = new BufferedReader(isr);
            String str = br.readLine();
            String hostName =  InetAddress.getLocalHost().getHostName();
            String str1 = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getDisplayName();
            map.put("Device",str1);
            map.put("HardwareAddress",getLocalHardwareAddress());
            map.put("LocalName",hostName);
            while(str!=null){
                if(str.trim().startsWith("IPv4".trim()))
                {
                    map.put("ipv4_address",str.trim().substring(str.indexOf(":") - 1,str.indexOf("(")-3));
//                     System.out.println("Local IPv4 Address:"+str.trim().substring(str.indexOf(":") - 1,str.indexOf("(")-3));
                }else if(str.trim().contains("IPv6")){
                    map.put("ipv6_address",str.trim().substring(str.indexOf(":") - 1,str.indexOf("(")-3));
//                    System.out.println("Local IPv6 Address:"+str.trim().substring(str.indexOf(":") - 1,str.indexOf("(")-3));
                }else if(str.trim().startsWith("DHCP") && str.trim().endsWith("1")){
                    map.put("dhcp_server",str.trim().substring(str.indexOf(":") - 1));
//                    System.out.println("DHCP Server:"+str.trim().substring(str.indexOf(":") - 1));
                }
                str = br.readLine();
            }
            br.close();
            isr.close();
            System.out.println(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    public String getLocalHardwareAddress(){
        StringBuilder sb = null;
        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] mac = new byte[0];
            mac = network.getHardwareAddress();
            sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getInternetInfo() throws IOException {
        String host = "https://api01.aliyun.venuscn.com";
        String path = "/ip";
        String method = "GET";
        String appcode = "15e0fd3542e24282bd461c31ba782dc4";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", "localhost");
        HttpResponse response = null;

        try {
            response = HttpUtils.doGet(host, path, method, headers, querys);
/*            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EntityUtils.toString(response.getEntity());
    }

    public Map<String, Object> getDisk() {
        Map<String, Object> map = new HashMap<>();
        File diskPartition = new File("C:/");
        long totalCapacity = diskPartition.getTotalSpace()/1024/1024/1024;
        long usableSpace = diskPartition.getUsableSpace()/1024/1024/1024;
        double rate = 1.0 - (double)usableSpace/(double)totalCapacity;
        map.put("c_rate",(int)(rate*100));
        map.put("c_left",(double)usableSpace);
        System.out.println((int)(rate*100));
        System.out.println((double)usableSpace);
        return map;
    }


}
