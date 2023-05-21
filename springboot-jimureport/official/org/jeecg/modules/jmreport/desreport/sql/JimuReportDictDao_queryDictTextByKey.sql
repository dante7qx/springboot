SElECT s.item_text FROM jimu_dict_item s 
WHERE s.dict_id = (SElECT id FROM jimu_dict WHERE dict_code = :code)
AND s.item_value = :key