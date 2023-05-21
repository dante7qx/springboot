SELECT a.id,a.param_name,a.param_txt,a.param_value
FROM jimu_report_db_param a
JOIN jimu_report_db b ON a.jimu_report_head_id = b.id
JOIN jimu_report c ON c.id = b.jimu_report_id
WHERE c.id = :reportId