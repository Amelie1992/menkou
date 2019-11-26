package com.wzlue.common.base;

import com.wzlue.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author chenshun
 * @email wzlue.com
 * @date 2017-03-14 23:15
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /*当前页码*/
    private Integer page;
    /*每页条数*/
    private Integer limit;

    public Query(Map<String, Object> params) {
        this.putAll(params);
        /*分页参数*/
        this.page = params.get("page") == null ? 1 : Integer.parseInt(params.get("page").toString());
        this.limit = params.get("limit") == null ? 10 : Integer.parseInt(params.get("limit").toString());
        this.put("offset", params.get("offset") == null ? ((page - 1) * limit) : Integer.parseInt(params.get("offset").toString()));
        this.put("page", page);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String) params.get("sidx");
        String order = (String) params.get("order");
        if (StringUtils.isNotBlank(sidx)) {
            this.put("sidx", SQLFilter.sqlInject(sidx));
        }
        if (StringUtils.isNotBlank(order)) {
            this.put("order", SQLFilter.sqlInject(order));
        }

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
