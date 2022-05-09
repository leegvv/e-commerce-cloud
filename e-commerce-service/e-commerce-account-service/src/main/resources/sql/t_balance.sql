CREATE TABLE IF NOT EXISTS `t_balance`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_Id`     bigint   NOT NULL DEFAULT 0 COMMENT '用户 id',
    `balance`     bigint   NOT NULL DEFAULT 0 COMMENT '账户余额',
    `create_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_id_key`(`user_Id`)
) ENGINE = InnoDB COMMENT = '用户账号余额表';