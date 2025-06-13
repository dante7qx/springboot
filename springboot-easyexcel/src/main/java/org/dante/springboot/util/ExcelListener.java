package org.dante.springboot.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;

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