# Cognito-Java-API

Documentation used for this project https://cognitohq.com/docs/

Run this project as Spring boot application.

Import it as Maven and Run as Spring boot application and modify the querty string as per your testing.

Note: Please make sure to verify the jobId for each url.

1) Create Profile Sample URL <br>
http://localhost:8080/createProfile

2) Create CandidateSearch Sample URL <br>
http://localhost:8080/createCandidateSearchJob?profileId=prf_2cVg4KBk5t4Pgm&phone=1123456789&firstname=John&middlename=J&lastname=Doe&day=10&month=10&year=2000&ssn=1234

3) Create Check CandidateSearchJob Sample URL <br>
http://localhost:8080/checkCandidateSearchJob?jobId=idsjob_4PnJUPpqydRigi

4) Create CandidateAssessment Sample URL <br>
http://localhost:8080/crateCandidateAssessmentJob?jobId=idnsch_99tKwuUVdMmRmG

5) Get CandidateSeachDetail Sample URL<br>
http://localhost:8080/getCandidateSearch?searchId=idnsch_99tKwuUVdMmRmG
