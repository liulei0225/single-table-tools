package com.qieren.tool.util;

import org.apache.commons.lang.StringUtils;

public class Util {
    public static String buildName(String tableName) {
        String name = tableName.toLowerCase().replaceAll(" ", "")//去空格
                .replaceAll("^[_0-9]*", "")//去开头的_,数字
                .replaceAll("_*$", "");//去结束的_
        boolean change = true;
        StringBuilder s = new StringBuilder();
        for (Character c : name.toCharArray()) {
            if (c.equals('_')) {
                change = true;
            } else if (change) {
                s.append(upper(c));
                change = false;
            } else {
                s.append(c);
            }
        }
        return s.toString();
    }

    public static String lowerFirst(String name) {
        char[] ns = name.toCharArray();
        if (ns[0] >= 'A' && ns[0] <= 'Z') {
            ns[0] = (char) (ns[0]+32);
        }
        return new String(ns);
    }

    public static String upperFirst(String name) {
        char[] ns = name.toCharArray();
        ns[0] = upper(ns[0]);
        return new String(ns);
    }

    private static char upper(char n) {
        if (n >= 'a' && n <= 'z') {
            return (char) (n -32);
        }
        return n;
    }

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    public static void main(String[] args) {
        System.out.println(lowerFirst("ABC"));
        System.out.println(lowerFirst("ZBC"));
        System.out.println(lowerFirst("aBC"));
        System.out.println(lowerFirst("zBC"));
        System.out.println(lowerFirst("1BC"));
        System.out.println(lowerFirst("9BC"));
        System.out.println(lowerFirst("!BC"));
    }
}