create table soup.member
(
    member_idx bigint       not null
        primary key,
    id         varchar(20)  not null,
    password   varchar(15)  not null,
    nickname   varchar(10)  not null,
    birthday   date         not null,
    gender     varchar(255) not null,
    oauth      varchar(255) not null default 'ORIGIN',
    role       varchar(255) not null default 'USER',
    created_at datetime(6)  null,
    updated_at datetime(6)  null
);

