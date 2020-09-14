package com.cqmi.db.mapper.demo;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import com.cqmi.db.mapper.sqlmapper.ISqlMapper;
import com.cqmi.db.mapper.util.MybatisUtils;

import java.util.List;

/**
 * @Auther:GongXingRui
 * @Date:2019/1/24
 * @Description:
 **/
public class TestMybatisDemo {
    private static Logger logger = LogManager.getLogger(TestMybatisDemo.class);

    // 直接执行SQL语句
    public void testMybatisSelectOne() {
        String sql = "select user_name from t_admin_user where id = 2";
        ISqlMapper sqlMapper = MybatisUtils.getMapper(ISqlMapper.class);
        String name = sqlMapper.selectOne(sql);
        logger.info(name);
    }

    // 多个查询
    public void testMybatisSelectList() {
        String sql = "select user_name from t_admin_user";
        ISqlMapper sqlMapper = MybatisUtils.getMapper(ISqlMapper.class);
        List list = sqlMapper.selectList(sql);
        logger.info(list);
    }

    // 删除与插入
    public void testMybatisDeleteInsert() {
        ISqlMapper sqlMapper = MybatisUtils.getMapper(ISqlMapper.class);

        String deleteSql = "delete from t_admin_user  WHERE user_name = 'testuser';";
        String insertSql = "insert into `t_admin_user` (`id`, `user_name`, `user_password`, `del_flag`, `create_time`, `update_time`) VALUES( null,'testuser','testuser123','0','2019-01-21 19:43:58','2019-01-22 19:44:03');";
        int n = sqlMapper.delete(deleteSql);
        logger.info("删除数据：" + n);
        n = sqlMapper.insert(insertSql);
        logger.info("插入数据：" + n);
//        MybatisUtils.getSession().commit();
    }

}
