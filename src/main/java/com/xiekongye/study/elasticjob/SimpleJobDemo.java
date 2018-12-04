package com.xiekongye.study.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lieying
 * date : 2018/12/3 22:42
 */
@Component
@Slf4j
public class SimpleJobDemo implements SimpleJob {

	/**
	 * 执行作业.
	 *
	 * @param shardingContext 分片上下文
	 */
	@Override
	public void execute(ShardingContext shardingContext) {

		log.info("Job start, 线程名：{}, 当前分片：{}", Thread.currentThread().getName(), shardingContext.getShardingItem());
		RepositoryIteratorForSingleTable<String> iteratorForSingleTable = new RepositoryIteratorForSingleTable<>(0, 5, rowBounds -> {

			List<String> result = new ArrayList<>(100);
			if (rowBounds.getOffset() + rowBounds.getLimit() <= 100) {
				result.add(String.format("添加一条数据，当前offset : %d, limit : %d", rowBounds.getOffset(), rowBounds.getLimit()));
			}

			return result;
		});

		while (iteratorForSingleTable.hasNext()) {
			List<String> result = iteratorForSingleTable.next();
			log.info("获取数据： {}, current index : {}", result, shardingContext.getShardingItem());
		}

		log.info("Job done : {}", shardingContext);
	}
}
