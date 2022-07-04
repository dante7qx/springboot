-- 查询每个人最近一次借书记录
select
	u.name,
	u.age,
	b.book_name,
	b.book_no,
	t.borrow_time
from
	(select row_number () over (partition by user_id order by borrow_time desc) rowid, * from t_borrow_record) t
	inner join t_userinfo u on u.id = t.user_id
	inner join t_bookinfo b on b.id = t.book_id
where
	t.rowid = 1