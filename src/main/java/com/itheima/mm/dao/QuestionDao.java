package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;

import java.util.List;

/**
 * @author: Vanbban
 * @create 2020-12-17 19:58
 */
public interface QuestionDao {
    long findCountByCourseId(Integer id);

    Long findBasicCount(QueryPageBean queryPageBean);

    List<Question> findBasicList(QueryPageBean queryPageBean);

    void add(Question question);
}
