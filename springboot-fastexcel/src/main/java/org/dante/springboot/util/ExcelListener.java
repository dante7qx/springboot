package org.dante.springboot.util;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.util.ListUtils;

import java.util.List;

public class ExcelListener<T> implements ReadListener<T> {

    private final List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(100);

    @Override
    public void invoke(T data, AnalysisContext context) {
        cachedDataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 所有数据解析完成后的操作
    }

    public List<T> getDataList() {
        return cachedDataList;
    }

}