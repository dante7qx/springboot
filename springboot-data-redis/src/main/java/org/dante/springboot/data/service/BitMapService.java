package org.dante.springboot.data.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Redis的Bitmap（位图）是一种数据结构，用于存储位级别的数据，通常用于处理大量的二进制标志或状态。
 * 它是一个紧凑的数据结构，用于表示一组二进制位，每个位都只能是0或1。
 * Redis的Bitmap可以用于各种用途，例如记录用户的行为、计数、过滤、查找等。
 * 
 * 参考资料：
 * 
 * - https://mp.weixin.qq.com/s/p5uHLerqYFIxCrP4xWDZlg
 * - https://segmentfault.com/a/1190000040177140
 * - https://www.6hu.cc/archives/164617.html
 * - https://segmentfault.com/u/magebyte
 * 
 * 
 * @author dante
 *
 */
@Service
public class BitMapService {

	private static final String USER_SIGN_KEY = "usersign:";

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 用户签到
	 * 
	 * 把 年和月 作为BitMap的key，然后保存到一个BitMap中，每次签到就到对应的位上把数字从0变为1，只要是1，就代表是这一天签到了，反之咋没有签到
	 * 
	 * @param userId
	 */
	public void sign(Long userId) {
		sign(userId, DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN));
	}
	
	public void sign(Long userId, String signDateStr) {
		// 1. 获取登录用户
		Long loginUserId = userId;
		// 2. 获取日期
		Date statDate = getValidSignDate(signDateStr);
		// 3. 拼接key
		String keySuffix = DateUtil.format(statDate, ":yyyyMM");
	    String key = USER_SIGN_KEY + loginUserId + keySuffix;
		// 4. 获取补签天
	    int dayOfMonth = DateUtil.dayOfMonth(statDate);
		// 5. 写入redis setbit key offset 1
		stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1L, true);
	}
	
	/**
	 * 总签到天数
	 * 
	 * @param userId
	 * @param yearMonth
	 * @return
	 */
	public Integer signAllCount(Long userId, String yearMonth) {
		return signCount(userId, yearMonth, false);
	}

	/**
	 * 统计连续签到天数
	 * 
	 * 从最后一次签到开始向前统计，直到遇到第一次未签到为止，计算总的签到次数，就是连续签到天数
	 * 
	 * 例如：1 1 1 1 1 0 0 0 0 1 1 0 0 1 1 0 1 1 0 1 1 1 1
	 * 
	 * @param userId
	 * @param month yyyyMM
	 * @return
	 */
	public Integer signContinuousCount(Long userId, String yearMonth) {
		return signCount(userId, yearMonth, true);
	}
	
	/**
	 * 统计签到次数
	 * 
	 * @param userId
	 * @param yearMonth
	 * @param continuous 连续签到
	 * @return
	 */
	public Integer signCount(Long userId, String yearMonth, boolean continuous) {
		// 1. 获取登录用户
	    Long loginUserId = userId; 
	    // 2. 获取日期
	    Date statDate = getValidStatDate(yearMonth);
	    // 3. 拼接key
	    String keySuffix = ":" + yearMonth;
	    String key = USER_SIGN_KEY + loginUserId + keySuffix;
	    if(! continuous) {
	    	return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes())).intValue();
	    } else {
	    	 // 4. 获取统计的截止天
		    int dayOfMonth = getValidStatDay(statDate);
	    	// 5. 获取指定月截至今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD sign:5:202301 GET u3 0
		    List<Long> result = stringRedisTemplate.opsForValue().bitField(
		        key,
		        BitFieldSubCommands.create()
		        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
		    // 没有任务签到结果
		    if (result == null || result.isEmpty()) {
		        return 0;
		    }
		    Long num = result.get(0);
		    if (num == null || num == 0) {
		        return 0;
		    }
		    // 6. 循环遍历
		    int count = 0;
		    while (true) {
		        // 6.1 让这个数字与1 做与运算，得到数字的最后一个bit位 判断这个数字是否为0
		        if ((num & 1) == 0) {
		            //如果为0，签到结束
	        		break ;
		        } else {
		            count ++;
		        }
		        num >>>= 1;
		    }
			
			return count;
	    }
	}
	
	/**
	 * 获取用户签到月份的BitMap
	 * 
	 * @param userId
	 * @param yearMonth
	 * @return
	 */
	public String getSingData(Long userId, String yearMonth) {
		String key = USER_SIGN_KEY + userId + ":" + yearMonth;
		String val = stringRedisTemplate.opsForValue().get(key);
		if(StrUtil.isEmpty(val)) {
			return null;	// 位图不存在或为空时返回 null
		}
		
		byte[] bitmapBytes = val.getBytes();
        int[] intArray = new int[bitmapBytes.length * 8]; // 假设每个字节表示8位
        int index = 0;

        for (byte b : bitmapBytes) {
            for (int bitIndex = 7; bitIndex >= 0; bitIndex--) {
                intArray[index] = (b >> bitIndex) & 1;
                index++;
            }
        }
        
		return Arrays.toString(intArray);
	}
	
	/**
	 * 获取合理的签到日期
	 * 
	 */
	private Date getValidSignDate(String dateStr) {
		Date result = DateUtil.date();
		Date date = DateUtil.parse(dateStr, DatePattern.PURE_DATE_PATTERN);
		if(DateUtil.compare(date, result) < 0) {
			result = date;
		}
		return result;
	}
	
	/**
	 * 获取合理的统计天
	 * 
	 * 超过当前日期，返回当前日期
	 * 
	 * @param yearMonth
	 * @return
	 */
	private Date getValidStatDate(String yearMonth) {
		Date result = DateUtil.date();
		Date date = DateUtil.parse(yearMonth, "yyyyMM");
		if(DateUtil.compare(date, result) < 0) {
			result = date;
		}
		return result;
	}
	
	private Integer getValidStatDay(Date statDate) {
		int statDay = DateUtil.dayOfMonth(DateUtil.endOfMonth(statDate));
			
		Date curDate = DateUtil.date();
		if(DateUtil.month(statDate) == DateUtil.month(curDate)) {
			statDay = DateUtil.dayOfMonth(curDate);
		}
		return statDay;
	}
	
}
