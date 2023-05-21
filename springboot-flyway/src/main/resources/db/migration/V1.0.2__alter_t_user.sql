-- alter table t_user add column address varchar(256) default '';


set @tablename = "t_user";
set @columnname = "address";
set @preparedstatement = (select if (
  (
    select count(*) from information_schema.columns
    where
      (table_name = @tablename)
      and (column_name = @columnname)
  ) > 0,
  "select 1",
  concat ("alter table ", @tablename, " add ", @columnname, " varchar(256) default '';")
));
prepare alterifnotexists from @preparedstatement;
execute alterifnotexists;
deallocate prepare alterifnotexists;