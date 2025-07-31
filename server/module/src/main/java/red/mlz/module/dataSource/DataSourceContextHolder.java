//package red.mlz.module.dataSource;
//
//
//public class DataSourceContextHolder {
//
//    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
//
//    public static void setDataSourceType(String dataSourceType) {
//        CONTEXT_HOLDER.set(dataSourceType);
//    }
//
//    public static String getDataSourceType() {
//        return CONTEXT_HOLDER.get();
//    }
//
//    public static void clearDataSourceType() {
//        CONTEXT_HOLDER.remove();
//    }
//}