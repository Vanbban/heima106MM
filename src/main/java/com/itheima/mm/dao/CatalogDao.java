package com.itheima.mm.dao;


import com.itheima.mm.pojo.Catalog;

import java.util.List;

/**
 * @author: Vanbban
 * @create 2020-12-17 19:58
 */
public interface CatalogDao {
    Long findCountByCourseId(Integer id);

    List<Catalog> findCatalogListByCourseId(Integer courseId);

}
