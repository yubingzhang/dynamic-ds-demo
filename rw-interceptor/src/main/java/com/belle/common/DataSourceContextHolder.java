package com.belle.common;

public class DataSourceContextHolder {
    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "readTestDb";

    public static final String MASTER_DS = "writeTestDb";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    //private static final ThreadLocal<String> USER_ID_LOCAL = new InheritableThreadLocal<>();

    // 设置数据源名
    public static void setDB(String dbType) {
        System.out.println("切换到{"+dbType+"}数据源");
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return null == contextHolder.get() ?
                DEFAULT_DS : contextHolder.get();
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
