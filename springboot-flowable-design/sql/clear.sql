-- 清理全部流程数据
truncate act_evt_log;
truncate act_hi_actinst;
truncate act_hi_attachment;
truncate act_hi_comment;
truncate act_hi_detail;
truncate act_hi_entitylink ;
truncate act_hi_identitylink;
truncate act_hi_procinst;
truncate act_hi_taskinst;
truncate act_hi_tsk_log;
truncate act_hi_varinst;
truncate act_id_bytearray;

truncate act_ru_variable;
truncate act_ru_identitylink;
truncate act_ru_deadletter_job;
truncate act_ru_actinst;
delete from act_ru_task;
delete from act_ru_execution;


-- 清理业务流程数据
delete from act_hi_actinst where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst);
delete from act_hi_actinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%';
delete from act_hi_comment where TASK_ID_ in (select ID_  from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_hi_comment where TASK_ID_ in (select TASK_ID_  from act_ru_actinst where  PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%' );
delete from act_hi_identitylink where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst);
delete from act_hi_identitylink where PROC_INST_ID_ in (select PROC_INST_ID_  from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_hi_identitylink where TASK_ID_ in (select ID_  from act_ru_task  where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst));
delete from act_hi_identitylink where TASK_ID_ in (select ID_  from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_hi_identitylink where TASK_ID_ in (select TASK_ID_  from act_ru_actinst where  PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%' ); 
delete from act_hi_procinst where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst);
delete from act_hi_procinst where PROC_INST_ID_ in (select PROC_INST_ID_  from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_hi_varinst where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst);
delete from act_hi_varinst where PROC_INST_ID_ in (select PROC_INST_ID_  from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_hi_varinst where PROC_INST_ID_ in (select PROC_INST_ID_  from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_ru_identitylink where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst);
delete from act_ru_identitylink where PROC_INST_ID_ in (select PROC_INST_ID_  from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_ru_identitylink where TASK_ID_  in (select ID_  from act_ru_task where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst));
delete from act_ru_identitylink where TASK_ID_  in (select ID_  from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_hi_taskinst where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst);
delete from act_hi_taskinst where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%';
delete from act_ru_variable where PROC_INST_ID_ in (select process_id  from biz_attendance_actinst);
delete from act_ru_variable where EXECUTION_ID_ in (select ID_ from act_ru_execution where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%');
delete from act_ru_task where PROC_INST_ID_  in (select process_id  from biz_attendance_actinst);
delete from act_ru_task where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%';
delete from act_ru_execution where PROC_INST_ID_  in (select process_id  from biz_attendance_actinst);
delete from act_ru_execution where PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%';
delete from act_ru_actinst where  PROC_DEF_ID_ like 'KQ%' or PROC_DEF_ID_ like 'electrician%';

delete from biz_attendance_record;
delete from biz_attendance_actinst;
delete from biz_attendance_seq;
delete from biz_leave;
delete from biz_checkin_abnormal;
delete from biz_electrician_base;
delete from biz_goout;
