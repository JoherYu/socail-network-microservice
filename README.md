# 基于Spring Boot和Spring Cloud的微服务架构Web项目
本项目为第三次改写项目[分相](https://github.com/JoherYu/flask_web_program_social_network)，将项目[social-network-SSM-Vue](https://github.com/JoherYu/social-network-SSM-Vue)改造成微服务架构（使用github作为[私人maven仓库](https://github.com/JoherYu/maven-private)）。
抽取异同部分实现，大致架构如下图所示：    
![Architecture](https://github.com/JoherYu/socail-network-microservice/blob/master/Architecture.jpg)
### 其中
*    主数据库储存依旧使用MySQL，搭建读写分离集群分担负载压力。Redis高可用集群主要用于代替session存储token和缓存。
*    图片改用FastDFS存储，搜索服务器采用Elastic Search。
*    使用Spring Cloud Config统一管理配置，并使用Spring Boot Admin和Zipkin进行链路追踪、状态检查等等。
*    其他服务使用高可用集群（因服务器性能不足，故只部署了单体版本，可通过http://39.106.36.194 查看本项目，测试帐号：a@a.com，测试密码：123456）。
*    负载均衡流程：Nginx→Zuul→服务消费者→服务生产者
---
### 主要服务改变：
*    **安全管理**

     CSRF验证在Zuul过滤器中进行，结合Redis进行单点登录，Shiro只用来生成密码暗文。权限拦截全部使用MVC拦截器进行，全局异常处理和跨域处理在zuul内处理。
*    **虚拟数据生成服务**

     在原服务的基础上增加Elastic Search Document导入功能，测试内代码用于生成Elastic Search Index和Type。
*    **注册服务**

     在原服务的基础上使用RabbitMQ进行邮件的异步发送及Elastic Search Document更新。使用Thymeleaf渲染邮件确认页面。

  
