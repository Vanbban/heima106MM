package com.itheima.mm.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.framework.Controller;
import com.itheima.framework.RequestMapping;
import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;
import com.sun.corba.se.impl.orbutil.closure.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author: Vanbban
 * @create 2020-12-18 19:46
 */
@Controller
public class QuestionConstroller {
    private QuestionService  questionService=new QuestionService();
     @RequestMapping("/question/addOrUpdate")
     public void addQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
         try {
             Question question = JsonUtils.parseJSON2Object(request, Question.class);
             //需要手动设置的数据: status=0、reviewStatus=0、createDate=当前时间、userId=当前用户id
             question.setStatus(Constants.QUESTION_PRE_PUBLISH);
             question.setReviewStatus(Constants.QUESTION_PRE_REVIEW);
             User user = (User) request.getSession().getAttribute(Constants.MM_USER_LOGIN);
             question.setCreateDate(DateUtils.parseDate2String(new Date()));
             question.setUserId(user.getId());
             questionService.addQuestion(question);

             JsonUtils.printResult(response,new Result(true,"添加试题成功"));
         } catch (Exception e) {
             e.printStackTrace();
             JsonUtils.printResult(response,new Result(false,"添加试题失败"));
         }
     }
    @RequestMapping("/question/basicList")
    public void basicList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            QueryPageBean queryPageBean = JsonUtils.parseJSON2Object(request, QueryPageBean.class);
            PageResult pageResult=questionService.findBasicList(queryPageBean);
            JsonUtils.printResult(response,new Result(true,"查询基础题库成功",pageResult));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询基础题库失败"));
        }
    }
}
