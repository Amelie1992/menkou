package com.wzlue.common.utils;

public class LocationUtils {
    private static double EARTH_RADIUS = 6378.137;  //地球半径千米

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lat1 用户纬度
     * @param lng1 用户经度
     * @param lat2 商家纬度
     * @param lng2 商家经度
     * @return 距离
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        // 纬度之差
        double a = radLat1 - radLat2;
        // 经度之差
        double b = rad(lng1) - rad(lng2);
        // 计算两点距离的公式
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        // 弧长乘地球半径, 返回单位: 千米
        s = s * EARTH_RADIUS;
//        s = Math.round(s * 10000d) / 10000d;
//        s = s * 1000;
        s = (double) Math.round(s * 100) / 100;
        return s;
    }

    public static void main(String[] args) {
        double distance = getDistance(34.2675560000, 108.9534750000,
                34.2464320000, 108.9534750000);
//        System.out.println("距离" + distance / 1000 + "公里");
        System.out.println("距离" + distance + "公里");

    }

}
