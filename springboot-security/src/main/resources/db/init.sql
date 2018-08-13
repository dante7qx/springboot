-- 权限
insert into t_authority(code, name, pid) values
('public', '用户管理', -1),
('sysmgr.user.query', '查询用户', 1),
('sysmgr.user.update', '更新用户', 1),
('sysmgr.user.delete', '删除用户', 1),

('public', '角色管理', -1),
('sysmgr.role.query', '查询角色', 5),
('sysmgr.role.update', '更新角色', 5),
('sysmgr.role.delete', '删除角色', 5),

('public', '资源管理', -1),
('sysmgr.resource.query', '查询资源', 9),
('sysmgr.resource.update', '更新资源', 9),
('sysmgr.resource.delete', '删除资源', 9)

('public', '权限管理', -1),
('sysmgr.authority.query', '查询权限', 13),
('sysmgr.authority.update', '更新权限', 13),
('sysmgr.authority.delete', '删除权限', 13)

-- 资源
insert into t_resource(name, url, pid, authority_id) values
('用户信息', '/user/query', null, 2)
('角色信息', '/role/query', null, 6)
('资源信息', '/menu/query', null, 10)

-- 资源权限
insert into t_authority_resource(authority_id, resource_id) values
(2, 1),
(6, 2),
(10, 3)

-- 角色
insert into t_role(name, role_desc) values
('系统管理员', '拥有所有的权限')
('用户管理员', '用户的权限')
('系统报表员', '所有的查询权限')

-- 角色权限
insert into t_role_authority(role_id, authority_id) values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(3, 2),
(3, 6),
(3, 10)

-- 用户
insert into t_user(account,name,password,salt,email) values
('dante','但丁','b4bdc8ca37dd7c2984b040b56361cb381258b39971b261b01108c3ce73d230217d58020a8857f505','DANTE-SHIRO','ch.sun@hnair.com'),
('sgx','三草肃','358233101bb1d4b32b6c9eb0eaa62f09ba4207fa889a500d048414962d643bd3625e16834ff84329','DANTE-SHIRO','sgx@163.com'),
('spring','牛B','358233101bb1d4b32b6c9eb0eaa62f09ba4207fa889a500d048414962d643bd3625e16834ff84329','DANTE-SHIRO','spring@git.com')

-- 用户角色
insert into t_user_role(user_id, role_id) values
(1, 1),
(2, 2),
(3, 3)
