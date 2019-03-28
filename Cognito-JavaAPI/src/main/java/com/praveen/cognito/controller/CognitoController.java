package com.praveen.cognito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.praveen.cognito.CognitoRestClient;
import com.praveen.cognito.model.CognitoResponse;

@RestController
public class CognitoController {

	@Autowired
	CognitoRestClient cognitoClient;
	
	@RequestMapping(value="/createProfile",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String createProfile() {
		
		try {
			return cognitoClient.createProfile();
		} catch (Throwable e) {
		
			e.printStackTrace();
		}
		return null;
		
	}
	

	@RequestMapping(value="/createCandidateSearchJob",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String createCandidateSearchJob(@RequestParam String profileId,@RequestParam String  phone,@RequestParam String  firstname,@RequestParam String  middlename,
			@RequestParam String lastname,@RequestParam Integer  day,@RequestParam Integer  month,@RequestParam Integer  year,@RequestParam String  ssn) {
		
		try {
			return cognitoClient.createCandidateSearchJob(profileId, "+1"+phone, firstname, middlename, lastname, day, month, year, ssn);
		} catch (Throwable e) {
		
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value="/checkCandidateSearchJob",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public CognitoResponse checkCandidateSearchJob(@RequestParam String jobId) {
		
		try {
			return cognitoClient.checkCandidateSearchJobStatus(jobId);
		} catch (Throwable e) {
		
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	@RequestMapping(value="/crateCandidateAssessmentJob",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public CognitoResponse crateCandidateAssessmentJob(@RequestParam String jobId) {
		
		try {
			return cognitoClient.createCandidateAssessment(jobId);
		} catch (Throwable e) {
		
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value="/getCandidateSearch",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public CognitoResponse getCandidateSearchJob(@RequestParam String searchId) {
		
		try {
			return cognitoClient.getCandidateSearch(searchId);
		} catch (Throwable e) {
		
			e.printStackTrace();
		}
		return null;
		
	}
}
