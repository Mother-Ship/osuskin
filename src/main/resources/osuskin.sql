create table sc_user_skin
(
    id bigint not null,
    is_deleted tinyint(1) default 0 not null,
    gmt_created datetime not null,
    gmd_modifyed datetime not null,
    user_id bigint not null,
    skin_id bigint not null,
    version int default 0 not null,
    constraint sc_user_skin_pk
        primary key (id)
)
    comment '用户皮肤表';

create unique index sc_user_skin_uk_uid_sid_version
    on sc_user_skin (user_id asc, skin_id asc, version desc);

create table sc_skin
(
    id bigint not null,
    is_deleted tinyint(1) default 0 not null,
    gmt_created datetime not null,
    gmd_modifyed datetime not null,
    name varchar(50) not null,
    author_id bigint default null,
    mode int not null,
    preview_image_url varchar(100)  not null,
    download_count bigint default 0 not null,
    constraint sc_user_skin_pk
        primary key (id)
)
    comment '皮肤信息表';


create table sc_skin_metadata
(
    id bigint not null,
    is_deleted tinyint(1) default 0 not null,
    gmt_created datetime not null,
    gmd_modifyed datetime not null,
    skin_id bigint not null,
    file_path varchar(50)  not null,
    file_md5_url varchar(100)  not null,
    constraint sc_user_skin_pk
        primary key (id)
)
    comment '皮肤元数据表';
create index sc_skin_metadata_ik_file_url
    on sc_skin_metadata (file_md5_url asc);
