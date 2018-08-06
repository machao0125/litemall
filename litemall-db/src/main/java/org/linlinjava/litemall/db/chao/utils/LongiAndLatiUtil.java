package org.linlinjava.litemall.db.chao.utils;

/**
 * Created by machao on 2018/8/5.
 */
public class LongiAndLatiUtil {

    //计算结果单位为米
    private static double EARTH_RADIUS = 6371.393;
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double getDistance(String lnglat1, String lnglat2){
        String[] split1 = lnglat1.split(",");
        String[] split2 = lnglat2.split(",");
        Double lat1 = Double.parseDouble(split1[1]);
        Double lng1 = Double.parseDouble(split1[0]);
        Double lat2 = Double.parseDouble(split2[1]);
        Double lng2 = Double.parseDouble(split2[0]);
        return distance(lat1,lng1,lat2,lng2);
    }
    /**
     * 计算两个经纬度之间的距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double distance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }

    public static void main(String[] args) {
        System.out.println(distance(28.615467,106.581515,29.615467,106.581515));
    }


}
