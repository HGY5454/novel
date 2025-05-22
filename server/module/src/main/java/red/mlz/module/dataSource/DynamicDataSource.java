package red.mlz.module.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return contextHolder.get();
    }
}
