package red.mlz.module.dataSource;


import red.mlz.module.dataSource.DataSourceTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：悬崖上的列车(Jiuling Huan)
 * @date ：Created in 2025/2/27
 * @slogan: 莫听穿林打叶声 何妨吟啸且徐行 竹杖芒鞋轻胜马 一蓑烟雨任平生
 * @email: 514082870@qq.com
 * @desc:
 **/
@Slf4j
public class DbContextHandler {
    /**
     * 存储数据库类型
     */
    private static final ThreadLocal CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源
     *
     * @param dbType 数据库类型
     */
    public static void setDbType(DataSourceTypeEnum dbType) {
        CONTEXT_HOLDER.set(dbType.getType());
    }

    /**
     * 取得当前数据源
     *
     * @return 数据源
     */
    public static String getDbType() {
        log.info("当前操作数据源为-->" + CONTEXT_HOLDER.get());
        return (String) CONTEXT_HOLDER.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        CONTEXT_HOLDER.remove();
    }
}

