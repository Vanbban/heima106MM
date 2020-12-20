package com.itheima.mm.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.framework.Controller;
import com.itheima.framework.RequestMapping;
import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.exceptions.AddSameCourseException;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;
import com.itheima.mm.utils.SqlSessionFactoryUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: Vanbban
 * @create 2020-12-16 19:10
 */
@Controller
public class CourseConstroller {
    private CourseService courseService=new CourseService();

    @RequestMapping("/course/findAll")
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取请求参数status的值
            String status = request.getParameter("status");
            //调用业务层的方法查询所有学科
            List<Course> courseList = courseService.findAll(status);
            JsonUtils.printResult(response,new Result(true,"查询所有学科成功",courseList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询所有学科失败"));
        }
    }

    @RequestMapping("/course/pageList")
    public void findByPage(HttpServletRequest request, HttpServletResponse response)throws IOException{
        try {
            //1. 获取请求参数
            QueryPageBean queryPageBean =JsonUtils.parseJSON2Object(request,QueryPageBean.class);
            //将isShow由int类型变成字符串类型
            Map map=queryPageBean.getQueryParams();
            if (map!=null) {
                map.put("isShow",map.get("isShow")+"");
            }
            //2. 调用业务层的方法查询
            PageResult pageResult=courseService.findByPage(queryPageBean);
            JsonUtils.printResult(response,new Result(true,"查询成功",pageResult));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询失败"));
        }

    }

    @RequestMapping("/course/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response)throws IOException{
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            courseService.deleteById(id);
            JsonUtils.printResult(response,new Result(true,"删除成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,e.getMessage()));
        }

    }
    @RequestMapping("/course/update")
    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Course course = JsonUtils.parseJSON2Object(request, Course.class);
            courseService.update(course);
            JsonUtils.printResult(response,new Result(true,"修改成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"修改失败"));
        }
    }

    @RequestMapping("/course/add")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Course course = JsonUtils.parseJSON2Object(request,Course.class);
            //添加createDate userId orderNo
            course.setCreateDate(DateUtils.parseDate2String(new Date()));
            User user =(User) request.getSession().getAttribute(Constants.MM_USER_LOGIN);
            course.setUserId(user.getId());
            course.setOrderNo(1);
            
            courseService.addCourse(course);

            JsonUtils.printResult(response,new Result(true,"添加成功"));

        } catch (AddSameCourseException e) {
            JsonUtils.printResult(response, new Result(false, e.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(true,"添加失败"));
        }


    }
}
