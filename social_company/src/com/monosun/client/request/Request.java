/**
 * 고객의 요구사항을 정의한 인터페이스
 */
package com.monosun.client.request;

import com.monosun.share.Job;

/**
 * @author 강진희
 * 요구사항 인터페이스
 * 고객의 요구사항에 필요한 항목
 */
public interface Request {
	/**
	 * Job을 추가
	 * @param job
	 */
	public void pushJob(Job job);
	
	/**
	 * 주어진 Job 가장 최근 값 리턴한다.
	 * @return
	 */
	public Job popJob();
		

}
