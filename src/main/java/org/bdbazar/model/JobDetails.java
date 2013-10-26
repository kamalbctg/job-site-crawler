package org.bdbazar.model;

import java.util.Date;

public class JobDetails {
	long jobId;
	int jobsite_id;
	int catId;
	String job_title;
	String company;
	String posting_link;
	Date last_date;
	Date posting_date;
	String education;
	String category;
	String data;

	public JobDetails(Long jobId, int jobsite_id, int catId, String job_title,
			String company, String posting_link, Date last_date,Date posting_date,
			String education, String category) {
		super();
		this.jobId = jobId;
		this.jobsite_id = jobsite_id;
		this.catId = catId;
		this.job_title = job_title;
		this.company = company;
		this.posting_link = posting_link;
		this.last_date = last_date;
		this.education = education;
		this.category = category;
		this.posting_date = posting_date;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public int getJobsite_id() {
		return jobsite_id;
	}

	public void setJobsite_id(int jobsite_id) {
		this.jobsite_id = jobsite_id;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getJob_title() {
		return job_title;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosting_link() {
		return posting_link;
	}

	public void setPosting_link(String posting_link) {
		this.posting_link = posting_link;
	}

	public Date getLast_date() {
		return last_date;
	}

	public void setLast_date(Date last_date) {
		this.last_date = last_date;
	}

	public Date getPosting_date() {
		return posting_date;
	}

	public void setPosting_date(Date posting_date) {
		this.posting_date = posting_date;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}



	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + catId;
		result = prime * result + (int) (jobId ^ (jobId >>> 32));
		result = prime * result
				+ ((posting_link == null) ? 0 : posting_link.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobDetails other = (JobDetails) obj;
		if (catId != other.catId)
			return false;
		if (jobId != other.jobId)
			return false;
		if (posting_link == null) {
			if (other.posting_link != null)
				return false;
		} else if (!posting_link.equals(other.posting_link))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobDetails [jobId=" + jobId + ", jobsite_id=" + jobsite_id
				+ ", catId=" + catId + ", job_title=" + job_title
				+ ", company=" + company + ", posting_link=" + posting_link
				+ ", last_date=" + last_date + ", posting_date=" + posting_date
				+ ", education=" + education + ", category=" + category
				+ ", data=" + data + "]";
	}

	
	
}
