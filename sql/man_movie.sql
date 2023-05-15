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
    is_delete    tinyint      null comment '逻辑删除(0:未删除 1:删除)'
)
    collate = utf8mb4_0900_ai_ci;

create table tag
(
    id   int          not null comment '主键Id'
        primary key,
    name varchar(255) not null comment '标签名'
)
    collate = utf8mb4_0900_ai_ci;

