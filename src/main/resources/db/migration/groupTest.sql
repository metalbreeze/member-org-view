update user set level = null , saleMoney=0, group_id=null ,
status = null, bonusMoney=0,feedbackMoney=0,position=null
where id >200;
truncate group1;
truncate operations;

update report_center set money1=0,money2=0,electricMoney=10000000.00
where id <5;
