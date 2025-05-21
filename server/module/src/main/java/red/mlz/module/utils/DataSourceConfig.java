package red.mlz.module.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    public DataSource dynamicDataSource(
            @Qualifier("master") DataSource master,
            @Qualifier("slave") DataSource slave) {
        DynamicDataSource ds = new DynamicDataSource();
        ds.setDefaultTargetDataSource(master);
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("master", master);
        dataSources.put("slave", slave);
        ds.setTargetDataSources(dataSources);
        return ds;
    }
}
