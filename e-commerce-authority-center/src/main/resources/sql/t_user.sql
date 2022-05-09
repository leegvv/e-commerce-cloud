CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`          bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `username`    varchar(64)   NOT NULL DEFAULT '' COMMENT '用户名',
    `password`    varchar(255)  NOT NULL DEFAULT '' COMMENT 'MD5 加密之后的密码',
    `extra_info`  varchar(1024) NOT NULL DEFAULT '' COMMENT '额外的信息',
    `created_by`  bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
    `created_date` datetime      NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '创建时间',
    `last_modified_by`  bigint(20) NOT NULL DEFAULT 0 COMMENT '最后更新人',
    `last_modified_date` datetime      NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE key `username`(`username`)
) ENGINE = InnoDB COMMENT='用户表';