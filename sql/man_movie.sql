create table actor
(
    id        int          not null comment '主键id'
        primary key,
    name      varchar(255) null comment '演员名',
    photo_url varchar(255) null comment '演员照片'
)
    collate = utf8mb4_0900_ai_ci;

drop table if exists episodes;
create table episodes
(
    id        bigint       not null comment '主键Id'
        primary key,
    movie_id  bigint       not null comment '影视id',
    title     varchar(255) not null comment '分集标题',
    movie_url varchar(255) null comment '视频地址',
    is_new    tinyint      null comment '是否为最新'
)
    collate = utf8mb4_0900_ai_ci;

create table movie_actor
(
    movie_id      int          not null comment '影视id',
    actor_id      int          not null comment '演员id',
    posts         int          null comment '职位',
    cosplay_name  varchar(255) null comment '扮演人名',
    cosplay_photo varchar(255) null,
    primary key (movie_id, actor_id)
)
    collate = utf8mb4_0900_ai_ci;

drop table if exists movie_info;
create table movie_info
(
    id           bigint       not null comment '主键id'
        primary key,
    name         varchar(255) not null comment '影视名',
    introduction varchar(255) null comment '影视介绍',
    banner_url   varchar(255) null comment '影片封面',
    score        int          null comment '影片评分',
    view_num     bigint       null comment '观看人数',
    type         int          null comment '影视类型',
    cid          varchar(255) null comment '腾讯视频cid',
    video_source varchar(255) null comment '视频来源(tencent: 来源于腾讯, iqy: 来源于爱奇艺, upload: 站长自己上传)',
    create_time  datetime     null comment '创建时间',
    create_by    bigint       null comment '创建人',
    update_time  datetime     null comment '更新时间',
    update_by    bigint       null comment '更新人',
    is_delete    tinyint default 0     null comment '逻辑删除(0:未删除 1:删除)'
)
    collate = utf8mb4_0900_ai_ci;

create table tag
(
    id   int          not null comment '主键Id'
        primary key,
    name varchar(255) not null comment '标签名'
)
    collate = utf8mb4_0900_ai_ci;

drop table if exists video_api;
create table video_api (
    id   bigint          not null comment '主键Id'
        primary key,
    name varchar(255) not null comment '视频解析接口名字',
    url  varchar(255) not null comment '视频解析接口地址',
    create_time  datetime     null comment '创建时间',
    create_by    bigint       null comment '创建人',
    update_time  datetime     null comment '更新时间',
    update_by    bigint       null comment '更新人',
    is_delete    tinyint default 0      null comment '逻辑删除(0:未删除 1:删除)'
) collate = utf8mb4_0900_ai_ci;



-- rbac模型

drop table if exists man_user;
create table man_user (
    id   bigint          not null comment '主键Id'
      primary key,
    username varchar(20) not null comment '用户名',
    password varchar(20) not null comment '密码',
    email varchar(255) null comment '邮箱',
    nickname varchar(255) null comment '昵称',
    gender tinyint default 0 null comment '性别(0:女, 1:男)',
    avatar varchar(255) null comment '头像',
    status tinyint default 1 null comment '状态(0:禁用, 1:正常)',
    create_time  datetime     null comment '创建时间',
    create_by    bigint       null comment '创建人',
    update_time  datetime     null comment '更新时间',
    update_by    bigint       null comment '更新人',
    is_delete    tinyint default 0      null comment '逻辑删除(0:未删除 1:删除)'
) collate = utf8mb4_0900_ai_ci;

drop table if exists man_user_role;
create table man_user_role (
    id   bigint          not null comment '主键Id'
      primary key,
    user_id bigint not null,
    role_id bigint not null
) collate = utf8mb4_0900_ai_ci;



drop table if exists man_role;
create table man_role (
    id   bigint          not null comment '主键Id'
      primary key,
    name varchar(20) not null comment '角色名',
    status tinyint default 1 null comment '角色状态(0:禁止, 1: 正常)',
    create_time  datetime     null comment '创建时间',
    create_by    bigint       null comment '创建人',
    update_time  datetime     null comment '更新时间',
    update_by    bigint       null comment '更新人',
    is_delete    tinyint default 0      null comment '逻辑删除(0:未删除 1:删除)'
) collate = utf8mb4_0900_ai_ci;

drop table if exists man_role_menu;
create table man_role_menu (
    id   bigint          not null comment '主键Id'
       primary key,
    menu_id bigint not null,
    role_id bigint not null
) collate = utf8mb4_0900_ai_ci;


-- 菜单, 组件路径, 路由, 权限字符串, icon, 类型
drop table if exists man_menu;
create table man_menu (
    id   bigint          not null comment '主键Id'
      primary key,
    title varchar(20) not null comment '菜单名',
    path  varchar(255) null comment '路由路径',
    component  varchar(255) null comment '组件路径',
    permission varchar(255) null comment '权限字符串',
    icon  varchar(255) null comment '菜单图标',
    type tinyint default 0 null comment '类型(0:目录, 1:菜单, 2:按钮)',
    parent_id bigint null comment '父菜单id',
    sort tinyint default 0 null comment '排序',
    status tinyint default 1 null comment '菜单状态(0:禁止, 1: 正常)',
    create_time  datetime     null comment '创建时间',
    create_by    bigint       null comment '创建人',
    update_time  datetime     null comment '更新时间',
    update_by    bigint       null comment '更新人',
    is_delete    tinyint default 0      null comment '逻辑删除(0:未删除 1:删除)'
) collate = utf8mb4_0900_ai_ci;

-- 文件表 存储上传的文件的信息
drop table if exists man_files;
create table man_files (
    id   bigint          not null comment '主键Id'
    primary key,
    name  varchar(255) not null comment '文件名',
    size bigint null comment '文件大小',
    hash  varchar(20)  null comment '文件hash',
    type tinyint default 0 null comment '文件类型',
    upload_time datetime null comment '上传时间',
    upload_by    bigint null comment '上传人',
    is_delete tinyint default 0      null comment '逻辑删除(0:未删除, 1: 已删除)'
);
