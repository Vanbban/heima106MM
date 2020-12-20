package com.itheima.mm.service;

import com.itheima.mm.dao.*;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.exceptions.AddSameCourseException;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * @author: Vanbban
 * @create 2020-12-16 19:12
 */
public class CourseService {
    public void addCourse(Course course) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        Course resultCourse = courseDao.findByName(course.getName());
        if (resultCourse !=null) {
            //该学科已存在
            throw new AddSameCourseException("该学科已经存在");
        }
        courseDao.addCourse(course);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }

    public void update(Course course) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        courseDao.update(course);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }

    public void deleteById(Integer id) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao=sqlSession.getMapper(CourseDao.class);
        CatalogDao catalogDao = sqlSession.getMapper(CatalogDao.class);
        TagDao tagDao = sqlSession.getMapper(TagDao.class);
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
        //1. 判断当前学科是否有关联的二级目录，如果有就不能删除
        Long catalogCount=catalogDao.findCountByCourseId(id);
        if (catalogCount !=0) {
            throw new RuntimeException("有关联二级目录,不能删除");
        }
        //2. 判断当前学科是否有关联的标签，如果有则不能删除
        Long tagCount=tagDao.findCountByCourseId(id);
        if (tagCount!=0) {
            throw new RuntimeException("有关联标签,不能删除");
        }
        //3. 判断当前学科是否有管关联的题目，如果有则不能删除
        long questionCount=questionDao.findCountByCourseId(id);
        if (questionCount!=0) {
            throw new RuntimeException("有关联问题,不能删除");
        }

        courseDao.deleteById(id);


        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }

    public PageResult findByPage(QueryPageBean queryPageBean) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        Long total=courseDao.findCourseCount(queryPageBean);
        List<Course>courseList=courseDao.findPageList(queryPageBean);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return new PageResult(total,courseList);
    }

    public List<Course> findAll(String status) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao= sqlSession.getMapper(CourseDao.class);

        List<Course> courseList=courseDao.findAll(status);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return courseList;
    }
}
