package com.networkinfo.controller;

import com.alibaba.fastjson.JSON;
import com.networkinfo.service.impl.MonitorServiceImpl;
import com.networkinfo.utils.IPUtils;
import com.sun.management.OperatingSystemMXBean;
import org.junit.Test;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class InfoController {


    @RequestMapping("/info")
    public String GetLocalNetworkInformation(){
        IPUtils ipUtils =new IPUtils();
        Map<String,Object> map = ipUtils.getLocalInfo();
        HashMap<String, Object> res = new HashMap<>();
        res.put("data", map);
        String res_string = JSON.toJSONString(res);
        return res_string;
    }

    @RequestMapping("/netInfo")
    public String GetNetworkInformation() throws IOException {
        IPUtils ipUtils =new IPUtils();
        return ipUtils.getInternetInfo();
    }

    @RequestMapping("/getSysInfo")
    public String GetSystemInformation() throws Exception {
        MonitorServiceImpl msi =new MonitorServiceImpl();
        return JSON.toJSONString(msi.getSystemInfo());
    }
    @RequestMapping("/getDisk")
    public String GetDisk(){
        IPUtils ipUtils =new IPUtils();
        return JSON.toJSONString(ipUtils.getDisk());
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

    public static String getV4IP() {
        String ip = "";
        String chinaz = "http://ip.chinaz.com";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
//System.out.println(inputLine.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipstr = m.group(1);
            ip = ipstr;
//System.out.println(ipstr);
        }
        return ip;
    }
    @Test
    public void test1() {
        System.out.println(getV4IP());
    }

}
