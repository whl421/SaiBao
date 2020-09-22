# SaiBao
this is the first project
author:whl
datetime:2020-09-14 

#2020-09-21
项目说明：开发环境JDK1.7
编译tomcat版本：tomcat7
此前是一个springMVC项目不是maven项目，现将项目转转化为maven项目

错误分析报告：
原项目中在index.jsp 中使用了${menue.class}由于class是关键字，顾报错：org.apache.jasper.JasperException: javax.el.ELException
现修改方式如下：将class修改为Class 