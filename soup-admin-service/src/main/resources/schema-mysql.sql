create table if not exists member
(
    member_idx       bigint auto_increment
        primary key,
    id               varchar(20)  not null,
    password         varchar(255) not null,
    nickname         varchar(10)  not null,
    birthday         varchar(10)  not null,
    gender           varchar(2)   not null,
    role             varchar(10)  not null default 'USER',
    oauth            varchar(10)  not null default 'ORIGIN',
    total_access_cnt bigint       not null,
    last_access_time datetime(6)  null,
    created_at       datetime(6)  null,
    updated_at       datetime(6)  null
);


create table if not exists theme
(
    idx        bigint auto_increment
        primary key,
    created_at datetime(6) null,
    title      varchar(50) null
);

create table if not exists theme_category
(
    idx           bigint auto_increment
        primary key,
    main_category varchar(20) null,
    sub_category  varchar(20) null,
    theme_idx     bigint      null,
    foreign key (theme_idx) references theme (idx)
);

create table if not exists banner
(
    idx          varchar(255) not null
        primary key,
    content_type varchar(20)  null,
    path         longtext     null,
    size         bigint       null,
    title        varchar(255) null,
    theme_idx    bigint       null,
    foreign key (theme_idx) references theme (idx)
);