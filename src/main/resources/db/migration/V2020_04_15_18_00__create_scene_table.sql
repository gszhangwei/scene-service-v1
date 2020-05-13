CREATE TABLE `scene`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `panorama_id` bigint(20) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  `is_initial_show` tinyint(1) NULL DEFAULT 0,
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  `photos` varchar(1500) DEFAULT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);
