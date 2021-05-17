package com.oldboy.spider.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.Resource;

/*
    公共类
    泛型类: ArrayList<E> public ListIterator<E> listIterator(int index)
 */
public class CommonDao<T> extends SqlSessionDaoSupport {

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public T find(Integer id) {
        return null;
    }

    public T findAll() {
        return null;
    }

    public void insert(T t, String sql) {
        getSqlSession().insert(sql);
    }
}
