package red.mlz.module.dataSource;



import red.mlz.module.dataSource.DbContextHandler;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author ：悬崖上的列车(Jiuling Huan)
 * @date ：Created in 2025/2/27
 * @slogan: 莫听穿林打叶声 何妨吟啸且徐行 竹杖芒鞋轻胜马 一蓑烟雨任平生
 * @email: 514082870@qq.com
 * @desc:
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DbContextHandler.getDbType();
        logger.info(typeKey);
        if (typeKey == null) {
            logger.info("无法确定数据源,重置为主库(写库)");
            DbContextHandler.setDbType(DataSourceTypeEnum.MASTER);
            typeKey = DataSourceTypeEnum.MASTER.getType();
        }
        return typeKey;
    }
}
