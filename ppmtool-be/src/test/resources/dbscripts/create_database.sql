CREATE DATABASE IF NOT EXISTS `ppmtool_db`;
CREATE USER 'ppmtooluser'@'localhost' IDENTIFIED BY 'ppmtooluser';

GRANT ALL PRIVILEGES ON ppmtool_db.* TO 'ppmtooluser'@'localhost' WITH GRANT OPTION;

# The default password will be the same username : ppmtooluser
