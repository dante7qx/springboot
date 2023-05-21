SELECT s.item_value as "value",s.item_text as "text" FROM jimu_dict_item s
WHERE dict_id = (SELECT id FROM jimu_dict WHERE dict_code = :code)
AND s.status='1'
ORDER BY s.sort_order ASC