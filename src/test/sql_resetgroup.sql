
UPDATE `user` set level = 'A',group_id=1 where id >=1 and id <=1;
UPDATE `user` set level = 'B',group_id=1 where id >=2 and id <=3;
UPDATE `user` set level = 'C',group_id=1 where id >=4 and id <=7;
UPDATE `user` set level = 'D',group_id=1 where id >=8 and id <=15;
UPDATE `user` set level = 'E',group_id=1 where id >=16 and id <=31;
UPDATE `user` set level = 'F',group_id=1 where id >=32 and id <=62;
UPDATE `user` set level = NULL,group_id=NULL,status=NULL where id >=63;
update `user`  set status = 'old' where id <=62;
update `group1` set enddate = null where id = 1 ;