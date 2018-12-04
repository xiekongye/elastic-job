package com.xiekongye.study.elasticjob;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author : lieying
 * date : 2018/12/4 19:57
 */
@FunctionalInterface
public interface RollNextHandler<E> {

	/**
	 * 获取下一批
	 * */
	List<E> rollNext(RowBounds rowBounds);

}
