package com.itheima.mm.service;

import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.QuestionItemDao;
import com.itheima.mm.dao.TagDao;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;

import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.QuestionItem;

import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;


import java.util.List;

/**
 * @author: Vanbban
 * @create 2020-12-18 19:51
 */
public class QuestionService {
    public PageResult findBasicList(QueryPageBean queryPageBean) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
        Long total=questionDao.findBasicCount(queryPageBean);
        List<Question> questionList=questionDao.findBasicList(queryPageBean);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return new PageResult(total,questionList);
    }

    public void addQuestion(Question question) throws Exception {
        SqlSession sqlSession =null;
        try {
            sqlSession = SqlSessionFactoryUtils.openSqlSession();
            QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
            QuestionItemDao questionItemDao = sqlSession.getMapper(QuestionItemDao.class);
            TagDao tagDao = sqlSession.getMapper(TagDao.class);
            //怎么来做添加或更新呢?如果question有id那么就是更新，如果没有id就是新增
            if (question.getId()==null || question.getId()==0) {
                //就是添加
                //1. 添加题目自身的信息,查询出它的自增长id
                questionDao.add(question);
                //2. 添加题目选项的信息
                List<QuestionItem> questionItemList = question.getQuestionItemList();
                //遍历出了每一个选项，添加每一个选项
                //缺少questionId,因为之前添加试题的时候已经查询出来了自增长id，所以使用question.getId()就能获取试题id
                for (QuestionItem questionItem : questionItemList) {
                    questionItem.setQuestionId(question.getId());
                    questionItemDao.add(questionItem);
                }
                //3. 添加标签的id
                List<Integer> tagIds = question.getTagIds();
                for (Integer tagId : tagIds) {
                    tagDao.addQuestionTag(tagId,question.getId());
                }

            }else {
                //就是更新(不做代码实现)
            }
            SqlSessionFactoryUtils.commitAndClose(sqlSession);
        } catch (Exception e) {
            e.printStackTrace();
            SqlSessionFactoryUtils.rollbackAndClose(sqlSession);
            throw new RuntimeException("添加试题失败");
        }


    }
}
