# base

#### 项目介绍
本项目基于前后端分离方案提供了中小型管理系统的基础现实，其中包括机构管理、角色管理、人员管理、菜单管理、字典管理的基础支持，使用本项目可以快速搭建基于springboot后端+ant design pro前端的项目，并且无需关心基础功能现实快速开发的目的。

#### 软件架构

- 前端基于蚂蚁金服的react框架界面组件 ant design pro；参考文档：
1. ant design：https://ant.design/docs/react/introduce-cn；
2. ant design pro： https://pro.ant.design/docs/getting-started-cn；
- 后端：
1. spring4
2. springBoot
3. springSecurity
4. Mybatis
5. 工具包基于hutool开源包，推荐先找需要的工具类，如无法找到再自行扩展；文档：http://hutool.mydoc.io/；

#### 安装教程

- 前端
1. 安装nodeJs环境，nodejs下载地址：http://nodejs.cn/download/；
2. nodeJs环境安装完成后在命令行中执行：npm config set registry https://registry.npm.taobao.org，将镜像更换为淘宝镜像；
3. 命令行中进入前端工程根目录中执行：npm i，安装工程依赖包；
4. 依赖包安装完成后执行启动命令：npm run start,启动完成后程序会自动打开浏览器访问；

- 后端
1. 安装IDEA开发环境，配置maven；
2. 部署方式和普通maven web工程一致，如需以spring boot jar方式部署，请自行去除相关依赖；
3. **本工程推荐使用IDEA集成开发环境进行开发**；

#### 使用说明

- 前端
- 后端

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
