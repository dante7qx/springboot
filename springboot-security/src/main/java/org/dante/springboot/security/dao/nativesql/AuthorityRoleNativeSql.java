package org.dante.springboot.security.dao.nativesql;

public interface AuthorityRoleNativeSql {
	public final static String FINDAUTHORITYROLEBYROLEID = "select t.id, t.name, t.code, t.authority_desc as authorityDesc, t.show_order as showOrder, t.pid, t1.role_id as roleId"
														 + " from t_authority t "
														 + " left join t_role_authority t1 on t.id = t1.authority_id and t1.role_id = ?1"
														 + " order by t.pid asc, t.show_order asc";
}
