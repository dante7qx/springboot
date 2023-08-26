package org.dante.springboot.dao;

import static com.mybatisflex.core.query.QueryMethods.avg;
import static com.mybatisflex.core.query.QueryMethods.max;
import static org.dante.springboot.po.table.AccountTableDef.ACCOUNT;

import java.util.List;

import org.dante.springboot.SpringbootMybatisFlexApplicationTests;
import org.dante.springboot.po.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;

import cn.hutool.core.lang.Console;

public class AccountMapperTests extends SpringbootMybatisFlexApplicationTests {

	@Autowired
	private AccountMapper accountMapper;
	
	@Test
	public void query() {
		QueryWrapper queryWrapper = QueryWrapper.create()
			.select(ACCOUNT.ID, ACCOUNT.USER_NAME, ACCOUNT.AGE, ACCOUNT.BIRTHDAY)
			.where(ACCOUNT.AGE.eq(18));
		Account account = accountMapper.selectOneByQuery(queryWrapper);
		Console.log(account);
	}
	
	@Test
	public void queryWithRelation() {
		QueryWrapper queryWrapper = QueryWrapper.create()
			.select(ACCOUNT.ID, ACCOUNT.USER_NAME, ACCOUNT.AGE, ACCOUNT.BIRTHDAY)
			.where(ACCOUNT.AGE.eq(18));
		Account account = accountMapper.selectOneWithRelationsByQuery(queryWrapper);
		Console.log(account);
	}

	
	@Test
	public void func() {
		List<Account> accounts = QueryChain.of(accountMapper)
			.select(ACCOUNT.ALL_COLUMNS, 
					max(ACCOUNT.AGE).as(Account::getMaxAge),
					avg(ACCOUNT.AGE).as(Account::getAvgAge))
			.where(ACCOUNT.ID.le(100))
			.groupBy(Account::getAge)
			.list();
		
		Console.log(accounts);
	}
	
}
