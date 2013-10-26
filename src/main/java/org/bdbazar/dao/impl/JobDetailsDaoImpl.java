package org.bdbazar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.bdbazar.Util;
import org.bdbazar.dao.JobDetailsDao;
import org.bdbazar.model.JobDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JobDetailsDaoImpl implements JobDetailsDao {
	static final Logger log = Logger.getLogger(JobDetailsDaoImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	private final String INSERT_JOBDETAIL = "INSERT INTO  JOBDETAILS" +
			" (JOB_TITLE,COMPANY,POSTING_LINK,LAST_DATE,POSTING_DATE,EDUCATION,CATEGORY,JOBSITE_ID,JOB_RELATED_DATA)"+
			"VALUES(?,?,?,?,?,?,?,?,?)";
	private final String IS_EXISTING_JOB = "SELECT count(*) FROM JOBDETAILS  WHERE JOBSITE_ID = ?  AND POSTING_LINK = ?"; 
	
	@Transactional
	public void insert(final JobDetails jobDetail) {
		PreparedStatementCreator insertStatementCreator = new PreparedStatementCreator() {			
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException {
				PreparedStatement saveJobDetail = connection.prepareStatement(INSERT_JOBDETAIL);
				saveJobDetail.setString(1, jobDetail.getJob_title());
				saveJobDetail.setString(2, jobDetail.getCompany());
				saveJobDetail.setString(3, jobDetail.getPosting_link());
				saveJobDetail.setDate(4, Util.convertDate(jobDetail.getLast_date()));
				saveJobDetail.setDate(5, Util.convertDate(jobDetail.getPosting_date()));
				saveJobDetail.setString(6, jobDetail.getEducation());
				saveJobDetail.setString(7, jobDetail.getCategory());
				saveJobDetail.setInt(8, jobDetail.getJobsite_id());
				saveJobDetail.setString(9, jobDetail.getData());				
				return saveJobDetail;
			}
		};
		
		jdbcTemplate.update(insertStatementCreator);
	}

	public boolean isExist(JobDetails jobDetail) {
		return jdbcTemplate.queryForInt(IS_EXISTING_JOB) == 0 ? false : true;		
	}

}
