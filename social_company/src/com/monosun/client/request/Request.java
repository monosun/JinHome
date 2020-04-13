/**
 * 
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
	
	public Job popJob();
		

}
