package com.xiekongye.study.elasticjob;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lieying
 * date : 2018/12/3 22:40
 */
@Configuration
public class ElasticJobConfig {

	private String zkAddress = "localhost:2180,localhost:2181,localhost:2182";

	private String namespace = "namespace";

	/**
	 * zk配置
	 * */
	@Bean
	public ZookeeperConfiguration zkConfig() {
		return new ZookeeperConfiguration(zkAddress, namespace);
	}

	/**
	 * 协调注册中心
	 * */
	@Bean
	public CoordinatorRegistryCenter regCenter(ZookeeperConfiguration config) {
		CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(config);
		registryCenter.init();
		return registryCenter;
	}

}
