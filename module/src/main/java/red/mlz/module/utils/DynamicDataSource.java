package red.mlz.module.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "master".equals(DbContextHolder.get()) ? "master" : "slave";
    }
}