package id.co.bsi.scm.h2h.api.servicesimpl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.bsi.scm.h2h.api.rest.dto.AppRequest;

@Service
public class ApiConsumeServiceImpl {
	private final static Logger LOGGER = LoggerFactory.getLogger(ApiConsumeServiceImpl.class.getName());

//	@Autowired
//	private ObjectMapper objectMapper;

//	@Value("${use.proxy}")
//	private boolean useProxy;
//	
//	@Value("${proxy.host}")
//	private String host;
//	
//	@Value("${proxy.port}")
//	private Integer port;

	@Autowired
	private RestTemplate restTemplate;

//	public  ResponseEntity<Object> postServiceMiddleware(AppRequest request, String url) {
//		Object response = new Object();
//		
//		try {
//			
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//			
//			LOGGER.info("[POST] " + url);
//			LOGGER.info("[REQUEST] " + objectMapper.writeValueAsString(request));
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			
//			HttpEntity<String> entity = new HttpEntity<String>(objectMapper.writeValueAsString(request), headers);
//			response = restTemplate.postForObject(url, entity, Object.class);
//
//			LOGGER.info("[RESPONSE] " + objectMapper.writeValueAsString(response));
//			
//			return new ResponseEntity<Object>(response, HttpStatus.OK);
//		} catch (BadRequest | JsonProcessingException e) {
//			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
//			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//		} catch (Forbidden e) {
//			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
//			return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
//		} catch (NotFound e) {
//			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
//			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
//		} catch (InternalServerError e) {
//			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
//			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		} catch (ServiceUnavailable e) {
//			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
//			return new ResponseEntity<Object>(response, HttpStatus.SERVICE_UNAVAILABLE);
//		}
//	}

	public ResponseEntity<Object> postService(AppRequest request, String url, String accessTime, String applicationId,
			String authSignature) {
		Object response = new Object();

		try {

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			LOGGER.info("----------------------------------LOGGER OUT START----------------------------------------------------------------------- ");
			LOGGER.info("[Url]           : " + url);
			LOGGER.info("[accessTime]    : " + accessTime);
			LOGGER.info("[applicationId] : " + applicationId);
			LOGGER.info("[authSignature] : " + authSignature);
			LOGGER.info("[REQUEST]       : " + objectMapper.writeValueAsString(request));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("accessTime", accessTime);
			headers.set("applicationId", applicationId);
			headers.set("authSignature", authSignature);

			HttpEntity<String> entity = new HttpEntity<String>(objectMapper.writeValueAsString(request), headers);
			response = restTemplate.postForObject(url, entity, Object.class);

			LOGGER.info("[RESPONSE]      : " + objectMapper.writeValueAsString(response));
			LOGGER.info("----------------------------------LOGGER OUT END----------------------------------------------------------------------- ");

			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (BadRequest | JsonProcessingException e) {
			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} catch (Forbidden e) {
			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
		} catch (NotFound e) {
			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		} catch (InternalServerError e) {
			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ServiceUnavailable e) {
			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			LOGGER.info("[ERROR] Function postServiceMiddleware : " + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

}
