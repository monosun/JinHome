/**
 * ���� �䱸������ ������ �������̽�
 */
package com.monosun.client.request;

import com.monosun.share.Job;

/**
 * @author ������
 * �䱸���� �������̽�
 * ���� �䱸���׿� �ʿ��� �׸�
 */
public interface Request {
	/**
	 * Job�� �߰�
	 * @param job
	 */
	public void pushJob(Job job);
	
	/**
	 * �־��� Job ���� �ֱ� �� �����Ѵ�.
	 * @return
	 */
	public Job popJob();
		

}
