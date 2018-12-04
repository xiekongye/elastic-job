package com.xiekongye.study.elasticjob;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lieying
 * date : 2018/12/3 22:41
 */
@Configuration
public class ElasticJobs {

	@Autowired
	private SimpleJob scanDbWrite2CodisJob;

	@Autowired
	private CoordinatorRegistryCenter zkRegisterCenter;

	private String cron = "*/10 * * * * ?";

	private Integer shardingCount = 2;

	private String shardingItemParameters = "0=自定义参数,1=分区1的自定义参数,9=自己定义的分区9的参数  ";

	@Autowired
	@Bean
	public JobScheduler jobScheduler(SimpleJob scanDbWrite2CodisJob) {
		JobScheduler jobScheduler = new SpringJobScheduler(
				scanDbWrite2CodisJob,
				zkRegisterCenter,
				createLiteJobConfiguration(scanDbWrite2CodisJob.getClass(),
						cron,
						shardingCount,
						shardingItemParameters));
		jobScheduler.init();
		return jobScheduler;
	}

	private LiteJobConfiguration createLiteJobConfiguration(final Class<? extends ElasticJob> jobClass,
	                                                        final String cron,
	                                                        final int shardingTotalCount,
	                                                        final String shardingItemParameters) {

		SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(
				JobCoreConfiguration.newBuilder(
						"Simple-test-job",
						cron,
						shardingTotalCount)
						.description("简单job")
						.shardingItemParameters(shardingItemParameters)
						.build(),
				jobClass.getCanonicalName());

		return LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();

	}
}
