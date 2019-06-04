package com.sharePhoto.common.service.ReadWriteSplittingService;

import afu.org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Joher
 * @data 2019/6/2
 **/
public class MyRoutingDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
