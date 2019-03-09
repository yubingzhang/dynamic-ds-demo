package com.belle.config;

import com.belle.common.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("数据源为"+ DataSourceContextHolder.getDB());
        return DataSourceContextHolder.getDB();
    }
}
