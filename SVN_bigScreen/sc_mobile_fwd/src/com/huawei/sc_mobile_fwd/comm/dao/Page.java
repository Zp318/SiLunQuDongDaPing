package com.huawei.sc_mobile_fwd.comm.dao;

/**
 * 分页对象。
 * 
 */
public class Page
{
    /**  默认的页大小 */
    private static final int DEFAULT_PAGE_SIZE = 20;
    
    /**  默认的当前页 */
    private static final int DEFAULT_PAGE_INDEX = 1;
    
    /** 总记录数 */
    private int totalCount;
    
    /** 总页数 */
    private int pageCount;
    
    /** * 页大小 */
    private int pageSize;
    
    /** * 当前页 */
    private int pageIndex;
    
    /** 默认构造函数 */
    public Page()
    {
        this(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
    }
    
    /** 
     * 构造函数。
     * @param pageIndex current page
     * @param pageSize page size
     */
    public Page(int pageIndex, int pageSize)
    {
        this.pageIndex = (pageIndex < 0) ? DEFAULT_PAGE_INDEX : pageIndex;
        this.pageSize = (pageSize < 0) ? DEFAULT_PAGE_SIZE : pageSize;
        this.totalCount = 0;
        this.pageCount = 0;
    }
    
    public int getTotalCount()
    {
        return totalCount;
    }
    
    /** 
     * setTotalCount<br>
     * @param totalCount total count record
     */
    public void setTotalCount(int totalCount)
    {
        this.totalCount = (totalCount <= 0) ? 0 : totalCount;
        this.pageCount = (totalCount <= 0) ? 1
                : ((totalCount % pageSize == 0) ? (totalCount / pageSize)
                        : (totalCount / pageSize) + 1);
    }
    
    public int getPageCount()
    {
        return pageCount;
    }
    
    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getPageIndex()
    {
        return pageIndex;
    }
    
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }
    
    /** {@inheritDoc}} */
    @Override
    public String toString()
    {
        return "Page [totalCount=" + totalCount + ", pageCount=" + pageCount
                + ", pageSize=" + pageSize + ", pageIndex=" + pageIndex + "]";
    }
}
