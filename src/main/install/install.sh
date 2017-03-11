apt-get update

apt-get install git

apt-get install maven

apt-get install openjdk-8-jdk

apt-get install mysql-server

mysqld -p -u root springmvchibernate


update user set authentication_string=password('root'), plugin='mysql_native_password' where user='root';
flush privileges;