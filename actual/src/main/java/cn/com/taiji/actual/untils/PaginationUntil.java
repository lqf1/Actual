package cn.com.taiji.actual.untils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxx
 * @version 1.0
 * @date 2018/12/16 21:27
 */
public class PaginationUntil {

    public static Pageable getPage(Map searchParameters){
        //初始化数据
        int page=0;
        int size=2;
        PageRequest pageable;
        //判断数据是否为空，不为空则赋值
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("page") != null) {
            page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
        }
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("pageSize") != null) {
            size = Integer.parseInt(searchParameters.get("pageSize").toString());
        }
        //设置页数最大最小值
        if (size < 1) {
            size = 1;
        }
        if (size > 100){
            size = 100;
        }
        //判断是否传入sort排序
        List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (orderMaps != null) {
            for (Map m : orderMaps) {
                if (m.get("field") == null){
                    continue;
                }
                String field = m.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = m.get("dir").toString();
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Sort.Order(Sort.Direction.DESC, field));
                    } else {
                        orders.add(new Sort.Order(Sort.Direction.ASC, field));
                    }
                }
            }
        }
        //生成pageable
        if (orders.size() > 0) {
            pageable = new PageRequest(page, size, new Sort(orders));
        } else {
            pageable = new PageRequest(page, size);
        }
        return pageable;
    }

}
