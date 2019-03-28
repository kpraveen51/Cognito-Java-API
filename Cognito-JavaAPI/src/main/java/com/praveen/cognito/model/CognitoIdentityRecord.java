package com.praveen.cognito.model;

import java.util.ArrayList;
import java.util.List;


public class CognitoIdentityRecord {

	private List<AddressCognito> addresses;
	private List<NameCognito> names;
	private List<SSN> ssns;
	private List<DOB> dobs;

	private Integer score;
	private String identityRecordId;

	public CognitoIdentityRecord() {

	}

	public CognitoIdentityRecord(String id) {
		this.setIdentityRecordId(id);
		addresses = new ArrayList<>();
		names = new ArrayList<>();
		ssns = new ArrayList<>();
		dobs = new ArrayList<>();
	}

	public String getIdentityRecordId() {
		return identityRecordId;
	}

	public void setIdentityRecordId(String identityRecordId) {
		this.identityRecordId = identityRecordId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public List<AddressCognito> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressCognito> addresses) {
		this.addresses = addresses;
	}

	public List<NameCognito> getNames() {
		return names;
	}

	public void setNames(List<NameCognito> names) {
		this.names = names;
	}

	public List<SSN> getSsns() {
		return ssns;
	}

	public void setSsns(List<SSN> ssns) {
		this.ssns = ssns;
	}

	public List<DOB> getDobs() {
		return dobs;
	}

	public void setDobs(List<DOB> dobs) {
		this.dobs = dobs;
	}

	public static class AddressCognito {
		private String street;
		private String city;
		private String subdivision;
		private String postal_code;
		private String country_code;

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getSubdivision() {
			return subdivision;
		}

		public void setSubdivision(String subdivision) {
			this.subdivision = subdivision;
		}

		public String getPostal_code() {
			return postal_code;
		}

		public void setPostal_code(String postal_code) {
			this.postal_code = postal_code;
		}

		public String getCountry_code() {
			return country_code;
		}

		public void setCountry_code(String country_code) {
			this.country_code = country_code;
		}
	}

	public static class NameCognito {
		private String first;
		private String middle;
		private String last;

		public String getFirst() {
			return first;
		}

		public void setFirst(String first) {
			this.first = first;
		}

		public String getMiddle() {
			return middle;
		}

		public void setMiddle(String middle) {
			this.middle = middle;
		}

		public String getLast() {
			return last;
		}

		public void setLast(String last) {
			this.last = last;
		}
	}

	public static class SSN {
		private String number;
		private String area;
		// private String group;
		private String serial;

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		/*
		 * public String getGroup() { return group; } public void setGroup(String group)
		 * { this.group = group; }
		 */
		public String getSerial() {
			return serial;
		}

		public void setSerial(String serial) {
			this.serial = serial;
		}
	}

	public static class DOB {
		private Integer day;
		private Integer month;
		private Integer year;

		public Integer getDay() {
			return day;
		}

		public void setDay(Integer day) {
			this.day = day;
		}

		public Integer getMonth() {
			return month;
		}

		public void setMonth(Integer month) {
			this.month = month;
		}

		public Integer getYear() {
			return year;
		}

		public void setYear(Integer year) {
			this.year = year;
		}
	}

	

}
