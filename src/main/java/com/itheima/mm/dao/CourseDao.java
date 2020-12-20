package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Vanbban
 * @create 2020-12-16 19:10
 */
public interface CourseDao {
    void addCourse(Course course);

    Course findByName(String name);

    void update(Course course);

    void deleteById(Integer id);

    Long findCourseCount(QueryPageBean queryPageBean);

    List<Course> findPageList(QueryPageBean queryPageBean);

    List<Course> findAll(@Param("status") String status);
}
