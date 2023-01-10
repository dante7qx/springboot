package org.dante.springboot.wordfliter;

import java.util.concurrent.TimeUnit;

import org.dante.springboot.docker.MsgVO;
import org.junit.jupiter.api.Test;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;

public class WordFilterTests {

	@Test
	public void test() {
		StopWatch watch = StopWatch.create("敏感词过滤");
		watch.start();
		SensitiveWordFilter.loadWordFromFile("SensitiveWordList.txt");

		MsgVO msg = new MsgVO();
		msg.setMsgId(IdUtil.nanoId());
		msg.setMsgName("毛泽东对于回民情况给出了指示意见");
		String str = JSONUtil.toJsonStr(msg);

		String result = SensitiveWordFilter.filter(str);
		System.out.println(result);
		watch.stop();
		Console.log(watch.getId(), watch.getTotal(TimeUnit.MILLISECONDS));

		boolean result1 = SensitiveWordFilter.allowed(str);
		System.out.println(result1);
		
	}
}
