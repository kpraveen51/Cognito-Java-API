package com.praveen.cognito.model;

import com.praveen.cognito.model.IdentitySearchRequest.Data;

public class CognitoResponse {
	
	private Data data;
	private Data[] included;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data[] getIncluded() {
		return included;
	}

	public void setIncluded(Data[] included) {
		this.included = included;
	}

	
	
	
	}

