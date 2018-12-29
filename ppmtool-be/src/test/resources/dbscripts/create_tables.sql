DROP TABLE IF EXISTS `project_task`;
DROP TABLE IF EXISTS `backlog`;
DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `user`;



CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `project_identifier` varchar(5) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_uk` (`project_identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `backlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `ptsequence` int(11) DEFAULT NULL,
  `project_identifier` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `backlog_key` (`project_id`),
  CONSTRAINT `backlog_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `project_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `acceptance_criteria` varchar(255) DEFAULT NULL,
  `due_date` datetime(6) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `project_identifier` varchar(255) DEFAULT NULL,
  `project_sequence` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `backlog_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `backlog_key` (`backlog_id`),
  CONSTRAINT `project_task_fk` FOREIGN KEY (`backlog_id`) REFERENCES `backlog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_uk` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;