package com.itheima.mm.controller;

import com.itheima.framework.Controller;
import com.itheima.framework.RequestMapping;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Vanbban
 * @create 2020-12-18 19:46
 */
@Controller
public class QuestionConstroller {
    private QuestionService  questionService=new QuestionService();
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
