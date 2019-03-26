package org.dante.springboot.algorithm;

import java.util.Collection;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

/**
 * 分表算法
 * 
 * @author dante
 *
 */
public final class ModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
		for (String each : tableNames) {
            //分片键
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
	}

}
