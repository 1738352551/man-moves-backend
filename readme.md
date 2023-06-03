# 慢慢电影网

## 1、 背景

## 2、数据库设计

- 影视信息表（影视名，影视简介，影视封面）

- 演职员表

- 标签表

- 剧集表

- 影视评论表

## 3、功能说明

本项目的视频来源共有两种来源:
1. 基于webMagic爬虫框架爬取的视频(自动化爬取, 定时任务, 主动爬取)
- 支持对腾讯视频全站爬取(已完成100%)
  - step 1: 库源爬取基本信息, 重要值 cid
  - step 2: 根据库源爬取影视对应的演职员信息、标签等信息(此处遇坑较多).
  - step 3: 爬取每一集地址, 使用网上公开的视频解析接口得到解析的url
- 支持对爱奇艺全站爬取（已完成80%）
2. 工作人员上传的视频
- 视频将会上传至oss中, 实际环境应当有独有的存储服务器
3. 视频一起看
4. 后端渲染图表
5. 影视信息管理(已完成)
6. 影视剧集管理(已完成)
7. 影视标签管理(已完成)
8. 视频解析接口管理(已完成): 系统的视频并非自己解析真链，采用的是网上的一些视频解析接口, 需要支持对接口的监控起是否还可用
9. 演职员信息管理(已完成)
10. 影视评论管理
11. 影视弹幕(暂定)
12. 待添加...
