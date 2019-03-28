package com.praveen.cognito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.praveen.cognito.model.CognitoIdentityRecord.DOB;
import com.praveen.cognito.model.CognitoIdentityRecord.NameCognito;
import com.praveen.cognito.model.CognitoIdentityRecord.SSN;
import com.praveen.cognito.model.CognitoResponse;
import com.praveen.cognito.model.IdentitySearchRequest;
import com.praveen.cognito.model.IdentitySearchRequest.Attribute;
import com.praveen.cognito.model.IdentitySearchRequest.Data;
import com.praveen.cognito.model.IdentitySearchRequest.Phone;
import com.praveen.cognito.model.IdentitySearchRequest.RelationshipEntry;
import com.praveen.cognito.model.IdentitySearchRequest.Relationships;
import com.praveen.cognito.util.Utils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@Component
public class CognitoRestClient {

	private static final Logger log = LoggerFactory.getLogger(CognitoRestClient.class);

	@Value("${cognito.key}")
	private String cognitokey;

	@Value("${cognito.secret}")
	private String cognitoSecret;

	@Value("${cognito.url}")
	private String cognitoUrl;

	public String createProfile() throws Throwable {
		try {
			String body = "{\"data\":{\"type\":\"profile\"}}";
			String request_target = "post /profiles";

			MessageDigest mdigest = MessageDigest.getInstance("SHA-256");
			String digest = "SHA-256=" + Base64.encodeBase64String(mdigest.digest(body.getBytes()));

			String pattern = "EEE, dd MMM yyyy HH:mm:ss z";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String date = simpleDateFormat.format(new Date());

			String content_type = "application/vnd.api+json";
			String accept_type = "application/vnd.api+json";
			String version = "2016-09-01";

			String signingstring = String.join("\n", new String[] {

					"(request-target): " + request_target, "date: " + date, "digest: " + digest

			});

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(cognitoSecret.getBytes(),
					"HmacSHA256");
			sha256_HMAC.init(secret_key);

			String signature = Base64.encodeBase64String(sha256_HMAC.doFinal(signingstring.getBytes()));

			String authorization = String.join(",", new String[] {

					"Signature keyId=\"" + cognitokey + "\"", "algorithm=\"hmac-sha256\"",
					"headers=\"(request-target) date digest\"", "signature=\"" + signature + "\""

			}

			);
			Client client = Client.create();

			WebResource webResource = client.resource(cognitoUrl + "/profiles");

			ClientResponse response = webResource.header("Date", date).header("Digest", digest)
					.header("Authorization", authorization).header("Content-Type", content_type)
					.header("Accept", accept_type).header("Cognito-Version", version)

					.post(ClientResponse.class, body);
			/*
			 * if (response.getStatus() != 201) { throw new
			 * RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
			 */

			System.out.println("Output from Server .... \n");
			String strOutput = response.getEntity(String.class);
			
			System.out.println(strOutput);
			
			CognitoResponse output = (CognitoResponse) Utils.stringJsonToObject(strOutput,CognitoResponse.class);
			System.out.println(output.getData().getId());
			return output.getData().getId();

		} catch (NoSuchAlgorithmException | InvalidKeyException | ClientHandlerException | UniformInterfaceException
				| IOException e) {
			log.error("Error in createProfile" + e.getMessage(), e);
			throw new Exception(e);
		}

	}

	public String createCandidateSearchJob(String profileId, String phone, String firstname, String middlename,
			String lastname, Integer day, Integer month, Integer year, String ssn)
			throws Exception, JsonProcessingException {
		try {

			IdentitySearchRequest identitySearchReq = new IdentitySearchRequest();

			Data data = new Data();
			data.setType("identity_search");

			data.setAttributes(new Attribute());
			data.getAttributes().setPhone(new Phone());
			data.getAttributes().getPhone().setNumber(phone);

			data.getAttributes().setName(new NameCognito());
			data.getAttributes().getName().setFirst(firstname);
			data.getAttributes().getName().setMiddle(middlename);
			data.getAttributes().getName().setLast(lastname);

			if (null != day) {
				data.getAttributes().setBirth(new DOB());
				data.getAttributes().getBirth().setDay(day);
				data.getAttributes().getBirth().setMonth(month);
				data.getAttributes().getBirth().setYear(year);

			}
			if (null != ssn) {
				data.getAttributes().setSsn(new SSN());
				data.getAttributes().getSsn().setSerial(ssn);

			}

			data.setRelationships(new Relationships());

			data.getRelationships().setProfile(new RelationshipEntry());
			data.getRelationships().getProfile().setData(new Data());
			data.getRelationships().getProfile().getData().setType("profile");
			data.getRelationships().getProfile().getData().setId(profileId);

			identitySearchReq.setData(data);

			String body = Utils.objectToJson(identitySearchReq);

			// String body = "{\"data\":{\"type\":\"identity_search\"}}";
			String request_target = "post /identity_searches";

			MessageDigest mdigest = MessageDigest.getInstance("SHA-256");
			String digest = "SHA-256=" + Base64.encodeBase64String(mdigest.digest(body.getBytes()));

			String pattern = "EEE, dd MMM yyyy HH:mm:ss z";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String date = simpleDateFormat.format(new Date());

			String content_type = "application/vnd.api+json";
			String accept_type = "application/vnd.api+json";
			String version = "2016-09-01";

			String signingstring = String.join("\n", new String[] {

					"(request-target): " + request_target, "date: " + date, "digest: " + digest

			});

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(cognitoSecret.getBytes(),
					"HmacSHA256");
			sha256_HMAC.init(secret_key);

			String signature = Base64.encodeBase64String(sha256_HMAC.doFinal(signingstring.getBytes()));

			String authorization = String.join(",", new String[] {

					"Signature keyId=\"" + cognitokey + "\"", "algorithm=\"hmac-sha256\"",
					"headers=\"(request-target) date digest\"", "signature=\"" + signature + "\""

			}

			);
			Client client = Client.create();

			WebResource webResource = client.resource(cognitoUrl + "/identity_searches");

			ClientResponse response = webResource.header("Date", date).header("Digest", digest)
					.header("Authorization", authorization).header("Content-Type", content_type)
					.header("Accept", accept_type).header("Cognito-Version", version).header("Prefer", "respond-async")

					.post(ClientResponse.class, body);
			/*
			 * if (response.getStatus() != 201) { throw new
			 * RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
			 */

			String strOutput = response.getEntity(String.class);
			
			System.out.println(strOutput);
			
			CognitoResponse output = (CognitoResponse) Utils.stringJsonToObject(strOutput,CognitoResponse.class);
			
			System.out.println(output);
			return output.getData().getId();

		} catch (NoSuchAlgorithmException | InvalidKeyException | ClientHandlerException | UniformInterfaceException
				| IOException e) {
			log.error("Error in createCandidateSearchJob " + e.getMessage(), e);
			throw new Exception(e);
		}

	}

	public CognitoResponse checkCandidateSearchJobStatus(String candidateSearchJobId)
			throws Exception, JsonProcessingException {
		try {

			// String body = "{\"data\":{\"type\":\"identity_searches\"}}";
			String body = "";
			String request_target = "get /identity_searches/jobs/" + candidateSearchJobId;

			MessageDigest mdigest = MessageDigest.getInstance("SHA-256");
			String digest = "SHA-256=" + Base64.encodeBase64String(mdigest.digest(body.getBytes()));

			String pattern = "EEE, dd MMM yyyy HH:mm:ss z";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String date = simpleDateFormat.format(new Date());

			String content_type = "application/vnd.api+json";
			String accept_type = "application/vnd.api+json";
			String version = "2016-09-01";

			String signingstring = String.join("\n", new String[] {

					"(request-target): " + request_target, "date: " + date, "digest: " + digest

			});

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(cognitoSecret.getBytes(),
					"HmacSHA256");
			sha256_HMAC.init(secret_key);

			String signature = Base64.encodeBase64String(sha256_HMAC.doFinal(signingstring.getBytes()));

			String authorization = String.join(",", new String[] {

					"Signature keyId=\"" + cognitokey + "\"", "algorithm=\"hmac-sha256\"",
					"headers=\"(request-target) date digest\"", "signature=\"" + signature + "\""

			}

			);

			//////////////////////////

			CloseableHttpClient client = HttpClientBuilder.create().build();

			HttpGet get = new HttpGet(cognitoUrl + "/identity_searches/jobs/" + candidateSearchJobId);
			get.setConfig(RequestConfig.custom().setRedirectsEnabled(false).build());

			get.addHeader("Date", date);
			get.addHeader("Digest", digest);
			get.addHeader("Authorization", authorization);
			get.addHeader("Content-Type", content_type);
			get.addHeader("Accept", accept_type);
			get.addHeader("Cognito-Version", version);

			HttpResponse response = client.execute(get);
			System.out.println(response.getEntity());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			StringBuffer responseContent = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				responseContent.append(line);
			}

			CognitoResponse output = (CognitoResponse) Utils.stringJsonToObject(responseContent.toString(),
					CognitoResponse.class);
			System.out.println(output);
			return output;

		} catch (NoSuchAlgorithmException | InvalidKeyException | ClientHandlerException | UniformInterfaceException
				| IOException e) {
			log.error("Error in checkCandidateSearchJobStatus" + e.getMessage(), e);
			throw new Exception(e);
		}

	}

	public CognitoResponse createCandidateAssessment(String candidateSearchId)
			throws Exception, JsonProcessingException {
		try {

			String body = "{\"data\":{\"type\":\"identity_assessment\"," + "\"relationships\": {"
					+ "      \"identity_search\": {" + "        \"data\": {"
					+ "          \"type\": \"identity_search\"," + "          \"id\": \"" + candidateSearchId + "\""
					+ "        }" + "      }}" + "    }" + "}";
			String request_target = "post /identity_assessments";

			MessageDigest mdigest = MessageDigest.getInstance("SHA-256");
			String digest = "SHA-256=" + Base64.encodeBase64String(mdigest.digest(body.getBytes()));

			String pattern = "EEE, dd MMM yyyy HH:mm:ss z";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String date = simpleDateFormat.format(new Date());

			String content_type = "application/vnd.api+json";
			String accept_type = "application/vnd.api+json";
			String version = "2016-09-01";

			String signingstring = String.join("\n", new String[] {

					"(request-target): " + request_target, "date: " + date, "digest: " + digest

			});

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(cognitoSecret.getBytes(),
					"HmacSHA256");
			sha256_HMAC.init(secret_key);

			String signature = Base64.encodeBase64String(sha256_HMAC.doFinal(signingstring.getBytes()));

			String authorization = String.join(",", new String[] {

					"Signature keyId=\"" + cognitokey + "\"", "algorithm=\"hmac-sha256\"",
					"headers=\"(request-target) date digest\"", "signature=\"" + signature + "\""

			}

			);
			Client client = Client.create();

			WebResource webResource = client.resource(cognitoUrl + "/identity_assessments");

			ClientResponse response = webResource.header("Date", date).header("Digest", digest)
					.header("Authorization", authorization).header("Content-Type", content_type)
					.header("Accept", accept_type).header("Cognito-Version", version)

					.post(ClientResponse.class, body);
			/*
			 * if (response.getStatus() != 201) { throw new
			 * RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
			 */

			System.out.println("Output from Server .... \n");
			String strOutput = response.getEntity(String.class);
			
			System.out.println(strOutput);
			
			CognitoResponse output = (CognitoResponse) Utils.stringJsonToObject(strOutput,CognitoResponse.class);
			
			System.out.println(output);
			return output;

		} catch (NoSuchAlgorithmException | InvalidKeyException | ClientHandlerException | UniformInterfaceException
				| IOException e) {
			log.error("Error in getCandidateSearch" + e.getMessage(), e);
			throw new Exception(e);
		}

	}

	public CognitoResponse getCandidateSearch(String candidateSearchId) throws Exception, JsonProcessingException {
		try {

			String body = "";
			String request_target = "get /identity_searches/" + candidateSearchId;

			MessageDigest mdigest = MessageDigest.getInstance("SHA-256");
			String digest = "SHA-256=" + Base64.encodeBase64String(mdigest.digest(body.getBytes()));

			String pattern = "EEE, dd MMM yyyy HH:mm:ss z";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String date = simpleDateFormat.format(new Date());

			String content_type = "application/vnd.api+json";
			String accept_type = "application/vnd.api+json";
			String version = "2016-09-01";

			String signingstring = String.join("\n", new String[] {

					"(request-target): " + request_target, "date: " + date, "digest: " + digest

			});

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(cognitoSecret.getBytes(),
					"HmacSHA256");
			sha256_HMAC.init(secret_key);

			String signature = Base64.encodeBase64String(sha256_HMAC.doFinal(signingstring.getBytes()));

			String authorization = String.join(",", new String[] {

					"Signature keyId=\"" + cognitokey + "\"", "algorithm=\"hmac-sha256\"",
					"headers=\"(request-target) date digest\"", "signature=\"" + signature + "\""

			}

			);
			Client client = Client.create();

			WebResource webResource = client.resource(cognitoUrl + "/identity_searches/" + candidateSearchId);

			ClientResponse response = webResource.header("Date", date).header("Digest", digest)
					.header("Authorization", authorization).header("Content-Type", content_type)
					.header("Accept", accept_type).header("Cognito-Version", version)

					.get(ClientResponse.class);
			/*
			 * if (response.getStatus() != 201) { throw new
			 * RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
			 */

			System.out.println("Output from Server .... \n");
			
			String strOutput = response.getEntity(String.class);
			
			System.out.println(strOutput);
			
			CognitoResponse output = (CognitoResponse) Utils.stringJsonToObject(strOutput,CognitoResponse.class);
			
			// System.out.println(output.getData().getId());
			return output;

		} catch (NoSuchAlgorithmException | InvalidKeyException | ClientHandlerException | UniformInterfaceException
				| IOException e) {
			log.error("Error in getCandidateSearch" + e.getMessage(), e);
			throw new Exception(e);
		}

	}

}

