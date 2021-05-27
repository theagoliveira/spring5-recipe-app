CREATE USER 'sfg_dev_user'@'localhost' IDENTIFIED BY 'guru';
CREATE USER 'sfg_prod_user'@'localhost' IDENTIFIED BY 'guru';

DROP DATABASE sfg_dev;
DROP DATABASE sfg_prod;

CREATE DATABASE sfg_dev;
CREATE DATABASE sfg_prod;

GRANT SELECT ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT INSERT ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT DELETE ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT UPDATE ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT SELECT ON sfg_prod.* TO 'sfg_prod_user'@'localhost';
GRANT INSERT ON sfg_prod.* TO 'sfg_prod_user'@'localhost';
GRANT DELETE ON sfg_prod.* TO 'sfg_prod_user'@'localhost';
GRANT UPDATE ON sfg_prod.* TO 'sfg_prod_user'@'localhost';
