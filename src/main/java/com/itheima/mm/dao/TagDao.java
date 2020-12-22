package com.itheima.mm.dao;

import com.itheima.mm.pojo.Catalog;
import com.itheima.mm.pojo.Tag;

import java.util.List;

/**
 * @author: Vanbban
 * @create 2020-12-17 19:58
 */
public interface TagDao {
    Long findCountByCourseId(Integer id);

    List<Tag> findTagListByCourseId(Integer courseId);

    void addQuestionTag(Integer tagId, Integer id);
}
