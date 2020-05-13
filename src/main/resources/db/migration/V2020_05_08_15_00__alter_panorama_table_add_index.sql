ALTER TABLE `panorama`
ADD INDEX `idx_update_time`(`update_time`) USING BTREE,
ADD UNIQUE INDEX `idx_panorama_url`(`panorama_url`) USING BTREE;
