package org.dante.springboot.algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Range;
import com.google.common.math.LongMath;

public class ShardingAlgorithm {
	
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) {
//		volumeRange();
		autoInterval();
	}
	
	/**
	 * 基于分片容量的范围分片，每个分片最多100条数据
	 * 
	 * 有Bug，应该进行如下修改
	 * 
	 *  - result.put(i + 1, Range.closedOpen(lower + i * volume, Math.min(lower + (i + 1) * volume, upper)));
	 *  + result.put(i + 1, Range.closedOpen(lower + i * volume + 1, Math.min(lower + (i + 1) * volume, upper)));
	 *
	 *  - result.put(partitionSize + 1, Range.atLeast(upper)); // 删除这一行 
	 */
	private static void volumeRange() {
		int lower = 100;
		int upper = 300;
		int volume = 100;
		int partitionSize = Math.toIntExact(LongMath.divide(upper - lower, volume, RoundingMode.CEILING));
        Map<Integer, Range<Comparable<?>>> result = new HashMap<>(partitionSize + 2, 1);
        result.put(0, Range.lessThan(lower));
        for (int i = 0; i < partitionSize; i++) {
//            result.put(i + 1, Range.closedOpen(lower + i * volume, Math.min(lower + (i + 1) * volume, upper)));
        	result.put(i + 1, Range.closedOpen(lower + i * volume + 1, Math.min(lower + (i + 1) * volume, upper)));
        }
//        result.put(partitionSize + 1, Range.atLeast(upper));
        System.out.println(result);
	}
	
	private static void autoInterval() {
		
		LocalDateTime dateTimeLower = LocalDateTime.parse("2021-01-01 00:00:00", DATE_TIME_FORMAT);
		long shardingSeconds = 7776000L;
		int autoTablesAmount = (int) (Math.ceil((double) (parseDate(dateTimeLower, "2023-12-31 23:59:59") / shardingSeconds)) + 2);
		String shardingValue = "2021-10-13 21:12:34";
		System.out.println(autoTablesAmount);
		String tableNameSuffix = String.valueOf(doSharding(parseDate(dateTimeLower, shardingValue), shardingSeconds, autoTablesAmount));
		 
		System.out.println(tableNameSuffix);
	}
	
	 private static long parseDate(LocalDateTime dateTimeLower, final Comparable<?> shardingValue) {
        LocalDateTime dateValue = LocalDateTime.from(DATE_TIME_FORMAT.parse(shardingValue.toString(), new ParsePosition(0)));
        return Duration.between(dateTimeLower, dateValue).toMillis() / 1000;
    }
	 
	 private static int doSharding(final long shardingValue, long shardingSeconds, int autoTablesAmount) {
        String position = new DecimalFormat("0.00").format((double) shardingValue / shardingSeconds);
        return Math.min(Math.max(0, (int) Math.ceil(Double.parseDouble(position))), autoTablesAmount - 1);
    }
	
}
