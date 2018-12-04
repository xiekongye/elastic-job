package com.xiekongye.study.elasticjob;

import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : lieying
 * date : 2018/12/4 19:55
 */
public class RepositoryIteratorForSingleTable<E> implements Iterator<List<E>> {

	//每次获取的大小
	private final int pageSize;

	private int offset;

	private boolean hasNext = true;

	private RollNextHandler<E> rollNextHandler;

	public RepositoryIteratorForSingleTable(int offset, int pageSize, RollNextHandler<E> rollNextHandler) {
		this.offset = offset;
		this.pageSize = pageSize;
		this.rollNextHandler = rollNextHandler;
	}

	@Override
	public boolean hasNext() {
		return this.hasNext;
	}


	@Override
	public List<E> next() {
		if (!hasNext) {
			return new ArrayList<>();
		}

		RowBounds rowBounds = new RowBounds(offset, pageSize);
		List<E> nextBatch = rollNextHandler.rollNext(rowBounds);

		offset += pageSize;//向后移offset

		if (!CollectionUtils.isEmpty(nextBatch)) {
			return nextBatch;
		}

		hasNext = false;
		return new ArrayList<>();
	}

}
