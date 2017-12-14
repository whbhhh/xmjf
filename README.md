# xmjf
小蚂金服

项目功能实现分两大块：web网站与后台管理，网站交互主要用于贷款项目展示，用户信息注册，登录与信息认证，充值，项目投标与收益展示行为。网站后台管理负责贷款                     项目维护(发起，审核，截标，与还款计划查询等操作)，用户信息维护，图片资源维护，资源权限控制等操作.

项目使用技术: 项目采用分布式技术 Dubbo+Zookeeper 搭建，将业务拆分为服务形式，方便后续对项目进行分布式部署，数据库采用MySql,后台框架使用当前比较成熟               的SSM（Spring+SpringMvc+Mybatis）来实现具体业务,前端视图技术web网站采用Freemarker模板，可以实现页面信息的静态化理.Js交互采用                       Jquery+Ajax以及相关js插件实现，网站后台使用BootStrap+Angula基于angular双向绑定技术快速开发后台模块相关功能. 
            开发工具是idea(也可使用 eclipce)。

项目环境搭建:项目搭建基于maven多模块,项目模块名称和关系如下:
             xmjf-par:父模块
             xmjf-api:服务接口定义子模块
             xmjf-service:接口服务实现子模块
             xmjf-admin:后台管理子模块
             xmjf-web:网站子模块
注意点:配置server 模块项目启动命令启动 server 项目 这里以 9090 端口启动(注意多模块项目启动子模块先进行 install 操作!，启动 server 项目前先启动 	         zookeeper注册中心).
