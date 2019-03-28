package com.praveen.cognito.model;

import com.praveen.cognito.model.CognitoIdentityRecord.DOB;
import com.praveen.cognito.model.CognitoIdentityRecord.NameCognito;
import com.praveen.cognito.model.CognitoIdentityRecord.SSN;

public class IdentitySearchRequest {

	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static class Data {
		private String type;
		private Attribute attributes;
		private Relationships relationships;
		private String id;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Attribute getAttributes() {
			return attributes;
		}

		public void setAttributes(Attribute attributes) {
			this.attributes = attributes;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Relationships getRelationships() {
			return relationships;
		}

		public void setRelationships(Relationships relationships) {
			this.relationships = relationships;
		}

	}

	public static class Attribute {

		private Phone phone;
		private NameCognito name;
		private DOB birth;
		private SSN ssn;
		private String created_at;
		private String updated_at;
		private String status;

		private String street;
		private String city;
		private String subdivision;
		private String postal_code;
		private String country_code;
		private String first;
		private String middle;
		private String last;
		private String number;
		private String area;
		private String group;
		private String serial;
		private Integer day;
		private Integer month;
		private Integer year;
		private Integer score;

		public Phone getPhone() {
			return phone;
		}

		public void setPhone(Phone phone) {
			this.phone = phone;
		}

		public String getCreated_at() {
			return created_at;
		}

		public void setCreated_at(String created_at) {
			this.created_at = created_at;
		}

		public String getUpdated_at() {
			return updated_at;
		}

		public void setUpdated_at(String updated_at) {
			this.updated_at = updated_at;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

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

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public String getSerial() {
			return serial;
		}

		public void setSerial(String serial) {
			this.serial = serial;
		}

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

		public Integer getScore() {
			return score;
		}

		public void setScore(Integer score) {
			this.score = score;
		}

		public NameCognito getName() {
			return name;
		}

		public void setName(NameCognito name) {
			this.name = name;
		}

		public DOB getBirth() {
			return birth;
		}

		public void setBirth(DOB birth) {
			this.birth = birth;
		}

		public SSN getSsn() {
			return ssn;
		}

		public void setSsn(SSN ssn) {
			this.ssn = ssn;
		}

	}

	public static class Phone {
		private String number;

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}
	}

	public static class Relationships {
		private RelationshipEntry profile;
		private RelationshipEntry identity_search;
		private RelationshipEntry identity_record;
		private RelationshipEntryArray addresses;
		private RelationshipEntryArray births;
		private RelationshipEntryArray deaths;
		private RelationshipEntryArray names;
		private RelationshipEntryArray phones;
		private RelationshipEntryArray ssns;

		public RelationshipEntry getProfile() {
			return profile;
		}

		public void setProfile(RelationshipEntry profile) {
			this.profile = profile;
		}

		public RelationshipEntry getIdentity_search() {
			return identity_search;
		}

		public void setIdentity_search(RelationshipEntry identity_search) {
			this.identity_search = identity_search;
		}

		public RelationshipEntry getIdentity_record() {
			return identity_record;
		}

		public void setIdentity_record(RelationshipEntry identity_record) {
			this.identity_record = identity_record;
		}

		public RelationshipEntryArray getAddresses() {
			return addresses;
		}

		public void setAddresses(RelationshipEntryArray addresses) {
			this.addresses = addresses;
		}

		public RelationshipEntryArray getBirths() {
			return births;
		}

		public void setBirths(RelationshipEntryArray births) {
			this.births = births;
		}

		public RelationshipEntryArray getDeaths() {
			return deaths;
		}

		public void setDeaths(RelationshipEntryArray deaths) {
			this.deaths = deaths;
		}

		public RelationshipEntryArray getNames() {
			return names;
		}

		public void setNames(RelationshipEntryArray names) {
			this.names = names;
		}

		public RelationshipEntryArray getPhones() {
			return phones;
		}

		public void setPhones(RelationshipEntryArray phones) {
			this.phones = phones;
		}

		public RelationshipEntryArray getSsns() {
			return ssns;
		}

		public void setSsns(RelationshipEntryArray ssns) {
			this.ssns = ssns;
		}

	}

	public static class RelationshipEntry {
		private Data data;

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}
	}

	public static class RelationshipEntryArray {
		private Data[] data;

		public Data[] getData() {
			return data;
		}

		public void setData(Data[] data) {
			this.data = data;
		}

	}

}
