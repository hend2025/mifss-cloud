package com.aeye.mifss.common.utils;

import cn.hsa.hsaf.core.framework.util.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public class PageUtils {
    public static <T> PageResult pageConvert(IPage<T> page) {
        PageResult pageResult = new PageResult();
        //List数据
        pageResult.setData(page.getRecords());
        //记录条数
        pageResult.setRecordCounts((int)page.getTotal());
        //每页大小
        pageResult.setPageSize((int)page.getSize());
        //当前多少页
        pageResult.setPageNum((int)page.getCurrent());
        //总分页大小
        pageResult.setPages((int)page.getPages());
        pageResult.setFirstPage(page.getCurrent() == 1);
        pageResult.setLastPage(page.getCurrent() >= page.getPages());
        return pageResult;
    }
    /**
     *PageResult<T> 转 PageResult<R>
     */
    public static  <T, R> PageResult<R> convertPageResult(PageResult<T> oldPageResult, List<R> collect) {
        PageResult<R> newPageResult = new PageResult<>();
        newPageResult.setData(collect);
        newPageResult.setPageNum(oldPageResult.getPageNum());
        newPageResult.setPageSize(oldPageResult.getPageSize());
        newPageResult.setPages(oldPageResult.getPages());
        newPageResult.setRecordCounts(oldPageResult.getRecordCounts());
        newPageResult.setFirstPage(oldPageResult.isFirstPage());
        newPageResult.setLastPage(oldPageResult.isLastPage());
        return newPageResult;
    }

    /**
     *IPage<T> 转 IPage<R>
     */
    public static  <T, R> IPage<R> convertIpage(IPage<T> oldPageResult, List<R> collect) {
        IPage<R> newPageResult = new Page<>();
        newPageResult.setRecords(collect);
        newPageResult.setPages(oldPageResult.getPages());
        newPageResult.setPages(oldPageResult.getPages());
        newPageResult.setTotal(oldPageResult.getTotal());
        newPageResult.setCurrent(oldPageResult.getCurrent());
        newPageResult.setSize(oldPageResult.getSize());
        return newPageResult;
    }
    
}
