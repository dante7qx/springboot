package org.dante.springboot.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;

public class WeatherCrawler {

	// 中国气象频道城市天气信息的基础URL
	private static final String BASE_URL = "http://www.weather.com.cn/weather/";
	// 中国气象频道城市选择页面URL （华北）
	private static final String CITY_LIST_URL = "http://www.weather.com.cn/textFC/hb.shtml";

	// 城市代码映射表（部分常用城市示例）
	private static final Map<String, String> CITY_CODES = MapUtil.builder(new HashMap<String, String>())
		.put("北京", "101010100")
		.put("上海", "101020100")
		.put("广州", "101280101")
		.put("深圳", "101280601")
		.put("杭州", "101210101")
		.put("南京", "101190101")
		.put("武汉", "101200101")
		.put("成都", "101270101")
		.put("重庆", "101040100")
		.put("西安", "101110101")
		.put("天水", "101160901")
		.build();

	public static String getWeatherInfoByCode(String cityName, String cityCode) throws IOException {
        // 构建URL
        String url = BASE_URL + cityCode + ".shtml";
        
        // 发送HTTP请求并获取网页内容
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .timeout(10000)
                .get();

        StringBuilder result = new StringBuilder();
        result.append(cityName).append("天气信息:\n");

        // 解析当天天气信息
        try {
            Element todayWeather = doc.selectFirst("div.today.clearfix");
            if (todayWeather != null) {
                Element dateInfo = todayWeather.selectFirst("h1");
                Element weatherInfo = todayWeather.selectFirst("p.wea");
                Element tempInfo = todayWeather.selectFirst("p.tem");
                Element windInfo = todayWeather.selectFirst("p.win");

                if (dateInfo != null) result.append("日期: ").append(dateInfo.text()).append("\n");
                if (weatherInfo != null) result.append("天气: ").append(weatherInfo.text()).append("\n");
                if (tempInfo != null) {
                    Element tempSpan = tempInfo.selectFirst("span");
                    Element tempI = tempInfo.selectFirst("i");
                    if (tempSpan != null && tempI != null) {
                        result.append("温度: ").append(tempSpan.text()).append("/").append(tempI.text()).append("\n");
                    }
                }
                if (windInfo != null) {
                    Element windStrong = windInfo.selectFirst("i");
                    if (windStrong != null) result.append("风力: ").append(windStrong.text()).append("\n");
                }
            }

            // 解析未来6天天气预报
            result.append("\n未来天气预报:\n");
            Elements forecast = doc.select("div.c7d > ul.t.clearfix li");
            for (Element day : forecast) {
                Element dateElement = day.selectFirst("h1");
                Element weaElement = day.selectFirst("p.wea");
                Element temElement = day.selectFirst("p.tem");
                Element winElement = day.selectFirst("p.win i");

                if (dateElement != null && weaElement != null && temElement != null) {
                    result.append(dateElement.text()).append(": ")
                          .append(weaElement.text()).append(", ")
                          .append(temElement.text());
                    
                    if (winElement != null) {
                        result.append(", ").append(winElement.text());
                    }
                    result.append("\n");
                }
            }
        } catch (Exception e) {
            result.append("解析天气信息时出错: ").append(e.getMessage());
        }

        return result.toString();
	}
	
	/**
     * 获取指定城市的天气信息
     * 
     * @param cityName 城市名称
     * @return 天气信息字符串
     * @throws IOException 网络连接错误
     * @throws IllegalArgumentException 城市不存在
     */
    public static String getWeatherInfo(String cityName) throws IOException {
        // 获取城市代码
        String cityCode = CITY_CODES.get(cityName);
        return getWeatherInfoByCode(cityName, cityCode);
    }
    
    /**
     * 从中国气象频道网站获取城市代码
     * 
     * @return 城市名称到城市代码的映射
     */
    public static Map<String, String> fetchCityCodes() throws IOException {
        Map<String, String> cityCodes = new HashMap<>();
        
        // 获取华北地区城市页面作为示例
        Document doc = Jsoup.connect(CITY_LIST_URL)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .timeout(10000)
                .get();
        
        // 查找城市链接
        Elements cityLinks = doc.select("div.conMidtab a");
        for (Element link : cityLinks) {
            String href = link.attr("href");		// 示例：http://www.weather.com.cn/weather/101010100.shtml
            String cityName = link.text().trim();
            if("详情".equals(cityName)) {
            	continue;
            }
            // 从链接中提取城市代码
            if (href.contains("/weather/") && href.endsWith(".shtml")) {
                String code = href.replace(BASE_URL, "").replace(".shtml", "");
                cityCodes.put(cityName, code);
            }
        }
        
        Console.log("成功获取 " + cityCodes.size() + " 个城市代码");
        return cityCodes;
    }
    
    /**
     * 获取所有地区的城市代码
     * 
     * 需要访问多个区域页面
     */
    public static Map<String, String> fetchAllCityCodes() throws IOException {
        Map<String, String> allCityCodes = new HashMap<>();
        
        // 中国气象频道的各个区域页面
        String[] regionUrls = {
            "http://www.weather.com.cn/textFC/hb.shtml",  // 华北
            "http://www.weather.com.cn/textFC/db.shtml",  // 东北
            "http://www.weather.com.cn/textFC/hd.shtml",  // 华东
            "http://www.weather.com.cn/textFC/hz.shtml",  // 华中
            "http://www.weather.com.cn/textFC/hn.shtml",  // 华南
            "http://www.weather.com.cn/textFC/xb.shtml",  // 西北
            "http://www.weather.com.cn/textFC/xn.shtml",  // 西南
            "http://www.weather.com.cn/textFC/gat.shtml"  // 港澳台
        };
        
        for (String url : regionUrls) {
            try {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                        .timeout(10000)
                        .get();
                
                Elements cityLinks = doc.select("div.conMidtab a");
                int count = 0;
                for (Element link : cityLinks) {
                    String href = link.attr("href");
                    String cityName = link.text().trim();
                    if(allCityCodes.containsKey(cityName)) {
                    	continue;
                    }
                    if("详情".equals(cityName)) {
                    	continue;
                    }
                    if (href.contains("/weather/") && href.endsWith(".shtml")) {
                    	count++;
                        String code = href.replace(BASE_URL, "").replace(".shtml", "");
                        allCityCodes.put(cityName, code);
                    }
                }
                
                Console.log("从 " + url + " 获取城市代码 " + count + " 个");
                // 防止频繁请求被封IP
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("获取城市代码失败: " + url + ", 错误: " + e.getMessage());
            }
        }
        
        Console.log("总共获取城市代码: " + allCityCodes.size() + " 个");
        return allCityCodes;
    }
    
    /**
     * 保存城市代码到文件
     * 这个方法需要添加文件操作相关代码
     */
    public static void saveCityCodes(Map<String, String> cityCodes, String filename) {
        // 实现保存城市代码到文件的代码
        // 例如使用BufferedWriter写入文件
    }
    

    public static void main(String[] args) {
        try {
            // 获取城市代码
        	/*
            Console.log("开始获取城市代码...");
            Map<String, String> cityCodes = fetchCityCodes();
            // 打印部分城市代码
            int count = 0;
            for (Map.Entry<String, String> entry : cityCodes.entrySet()) {
				Console.log(entry.getKey() + "(" + PinyinUtil.getPinyin(entry.getKey(), "") + ")" + ": " + entry.getValue());
                if (++count >= 10) break; // 只打印前10个
            }
            */
            
            // 如果想获取所有城市代码并保存
            /*
             Map<String, String> allCodes = fetchAllCityCodes();
             saveCityCodes(allCodes, "city_codes.txt");
             */
            
            // 测试获取北京的天气信息
//            Console.log("\n获取北京天气信息:");
            String weatherInfo = getWeatherInfo("北京");
            Console.log(weatherInfo);
            
            // 测试获取其他城市的天气
            /*
            if (args.length > 0) {
                String city = args[0];
                Console.log("\n获取" + city + "天气信息:");
                weatherInfo = getWeatherInfo(city);
                Console.log(weatherInfo);
            }
            */
            
        } catch (Exception e) {
            System.err.println("程序执行失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
