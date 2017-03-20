apt-get update

apt-get  -y install git

apt-get  -y install maven

apt-get  -y install openjdk-8-jdk

apt-get  -y install mysql-server

mysqld -p -u root springmvchibernate


update mysql.user set authentication_string=password('root'), plugin='mysql_native_password' where user='root';
flush privileges;


echo "create database springmvchibernate" | mysql -u root -p