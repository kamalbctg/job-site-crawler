package org.bdbazar.dao;

import org.bdbazar.model.JobDetails;

public interface JobDetailsDao {
	public void insert(JobDetails jobDetail);
	public boolean isExist(JobDetails jobDetail);
}
