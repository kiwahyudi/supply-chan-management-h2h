package id.co.bsi.scm.h2h.api.servicesimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import id.co.bsi.scm.h2h.api.model.AccessControl;
import id.co.bsi.scm.h2h.api.model.ApiMappingUrl;
import id.co.bsi.scm.h2h.api.repository.AccessControlRepository;
import id.co.bsi.scm.h2h.api.repository.ApiMappingUrlRepository;
import id.co.bsi.scm.h2h.api.rest.dto.AccessLoginRequest;
import id.co.bsi.scm.h2h.api.rest.dto.AppRequest;
import id.co.bsi.scm.h2h.api.rest.dto.ErrorRespObj;
import id.co.bsi.scm.h2h.api.util.AppsUtil;
import id.co.bsi.scm.h2h.api.util.DateUtil;
import id.co.bsi.scm.h2h.api.util.GenerateNumberSequenceUtil;
import id.co.bsi.scm.h2h.api.util.SHA512Util;

@Service
public class AutenticationUserServiceImpl {

	@Autowired
	private ApiConsumeServiceImpl apiConsumeServiceImpl;
	
	@Autowired
	private AccessControlRepository accessControlRepository;
	
	@Autowired
	private ApiMappingUrlRepository apiMappingUrlRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SupplyChainManagementServiceImpl.class.getName());

	public Object getAccess(AccessLoginRequest accessLoginRequest) {
		
		return new Object();
	}
	
	public ResponseEntity<Object> getAutentication(AppRequest appRequest) {
		Map<String, Object> resp = new HashMap<>();
		//String accessCode = reqHead.getAccessCode();
		
		return new ResponseEntity<Object>(setErrorResp(resp, AppsUtil.Error_Header_Access_Failed),
				HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Object> validateHeader(String accessTime, String applicationId, String authSignature, 
												AppRequest appRequest, String serviceCode) throws JsonMappingException, JsonProcessingException {
		
		//print LOG
		printHeaderLoggerIN(accessTime, applicationId, authSignature, serviceCode, appRequest);
		
		Map<String, Object> resp = new HashMap<>();
		
		String url = new String();
		
		String obj = new Gson().toJson(appRequest.getRequestData());
		JsonObject jobj = new Gson().fromJson(obj, JsonObject.class);
		
		//setReference Number
		appRequest.getRequestHeader().setReferenceNo(GenerateNumberSequenceUtil.refrenceNumber());
			
		AccessControl accControlNasabah = new AccessControl();
							
		//validate Header			
		if(Objects.isNull(accessTime) || accessTime.length() == 0) {
			return new ResponseEntity<Object>(setErrorResp(resp,AppsUtil.Error_Header_AccessTime),
						HttpStatus.BAD_REQUEST);
		}
		if(Objects.isNull(applicationId) || applicationId.length() == 0) {
			return new ResponseEntity<Object>(setErrorResp(resp,AppsUtil.Error_Header_ApplicationId),
					HttpStatus.BAD_REQUEST);
		}
		
		if(Objects.isNull(authSignature) || authSignature.length() == 0) {
			return new ResponseEntity<Object>(setErrorResp(resp,AppsUtil.Error_Header_AuthSignature),
					HttpStatus.BAD_REQUEST);
		}
		
		// Validate Access Control Nasabah	
		accControlNasabah = accessControlRepository.findByApplicationId(applicationId);
		if(accControlNasabah == null) {
			return new ResponseEntity<Object>(setErrorResp(resp,AppsUtil.Error_AppId),
					HttpStatus.NOT_FOUND);
		}else {
			//validate nasabah				
			String signatureNasabah = SHA512Util.encryptThisString(
					  "apiSecret=" + accControlNasabah.getSecret() + 
					  "&accessTime="+ accessTime +
					  "&apiKey="+ accControlNasabah.getKey() +
					  "&applicationId=" +applicationId);
			
			if(!signatureNasabah.equalsIgnoreCase(authSignature)) {
				return new ResponseEntity<Object>(setErrorResp(resp,AppsUtil.Error_Authorization_Failed),
						HttpStatus.BAD_REQUEST);
			}
		}		
			
		ApiMappingUrl apiMappingUrl = urlAndvalidateTypeIsDF(jobj, serviceCode, accControlNasabah.getApplicationId());
		
		Object objtReqData = objectMapper.readValue(jobj.toString(), Object.class);
		appRequest.setRequestData(objtReqData);
		
		if(apiMappingUrl != null) {
			//setUrl from comcode
			url = apiMappingUrl.getUrl(); 
		}else {
			 return new ResponseEntity<Object>(setErrorResp(resp,AppsUtil.Error_AppId),
						HttpStatus.BAD_REQUEST);
		}	
		
		// Validate Access Control	DVC
		AccessControl accControlDVC = accessControlRepository.findByName(AppsUtil.nameAppsDVC);
				
		String signature = SHA512Util.encryptThisString(
				  "apiSecret=" + accControlDVC.getSecret() + 
				  "&accessTime="+ DateUtil.getTimeNowMilisecon() +
				  "&apiKey="+ accControlDVC.getKey() +
				  "&applicationId=" +accControlDVC.getApplicationId()).toUpperCase();
			
		return apiConsumeServiceImpl.postService(appRequest, url, accessTime, applicationId, signature);
	}

	public ResponseEntity<Object> validateCallBack(AppRequest appRequest, String serviceCode)
			throws JsonMappingException, JsonProcessingException {
		Map<String, Object> resp = new HashMap<>();

		String url = new String();

		String obj = new Gson().toJson(appRequest.getRequestData());
		JsonObject jobj = new Gson().fromJson(obj, JsonObject.class);

		//setReference Number
		appRequest.getRequestHeader().setReferenceNo(GenerateNumberSequenceUtil.refrenceNumber());

		ApiMappingUrl apiMappingUrl = urlAndvalidateTypeIsDFCallback(jobj, serviceCode);

		if (apiMappingUrl != null) {
			//setUrl from comcode
			url = apiMappingUrl.getUrl();
		} else {
			return new ResponseEntity<Object>(
					setErrorRespComCode(resp, appRequest.getRequestHeader().getReferenceNo()),
					HttpStatus.BAD_REQUEST);
		}

		// Validate Access Control	Nasabah
		AccessControl accControlNasabah = accessControlRepository.findByApplicationId(apiMappingUrl.getApplicationId());
				
		String accessTime =  String.valueOf(DateUtil.getTimeNowMilisecon());
		String signature = SHA512Util.encryptThisString(
				  "apiSecret=" + accControlNasabah.getSecret() + 
				  "&accessTime="+ accessTime +
				  "&apiKey="+ accControlNasabah.getKey() +
				  "&applicationId=" +accControlNasabah.getApplicationId()).toUpperCase();
			
		return apiConsumeServiceImpl.postService(appRequest, url, accessTime, apiMappingUrl.getApplicationId(), signature);
		
	}
	
	
	public Map<String, Object> setErrorResp(Map<String, Object> resp, String messageError){
		ErrorRespObj error = new ErrorRespObj();
		error.setErrorCode(AppsUtil.Error_Code);
		error.setErrorMessage(messageError);
		resp.put("error", error);
		resp.put("responseMsg", "processed failed!");
		resp.put("responseCode", AppsUtil.Response_Error_Code);
		
		LOGGER.info("[ERROR] " + new Gson().toJson(resp));
		return resp;
	}
	
	public Map<String, Object> setErrorRespComCode(Map<String, Object> resp, String refNo){
		resp.put("responseCode", AppsUtil.Response_Success_Code);
		resp.put("responseMsg", AppsUtil.Error_ComCode);
		resp.put("orgReferenceNo", refNo);
		LOGGER.info("[COMCODE NULL] " + new Gson().toJson(resp));
		return resp;
	}
	
	
	public ApiMappingUrl urlAndvalidateTypeIsDF(JsonObject jobj, String serviceCode, String applicationId){
		boolean isDF = false;
		
		/**
		 * SENDINVOICE
			-> jika Produk type DF /DF maka hilangkan field product_type, invoiceAccAmt, docType, document ke dvc dengan url sendinvoice DF
			-> jika Produk selain DF maka passtruu ke url sendinvoice gabungan  
		 *	TRANSACTIONINQUIRY
			-> jika Produk type DF /DF maka hilangkan field product_type ke dvc dengan url sendinvoice DF
			-> jika Produk selain DF maka passtruu ke url sendinvoice gabungan  
		 * 	LIMITINQUIRY
			-> jika Produk type DF /DF maka hilangkan field product_type ke dvc dengan url sendinvoice DF (masih menunggu spec)
			-> jika Produk selain DF maka passtruu ke url sendinvoice gabungan  (masih menunggu spec)
		**/
		
		String productType  = jobj.get("productType").getAsString();

		//validate fied for df or all
		if(serviceCode.equals(AppsUtil.sendInvoice)) {
			if(productType.equals(AppsUtil.Product_type_DF)) {
				jobj.getAsJsonObject().remove("invoiceAccAmt");
				jobj.getAsJsonObject().remove("docType");
				jobj.getAsJsonObject().remove("productType");
				isDF = true;
			}	
		}else {
			if(productType.equals(AppsUtil.Product_type_DF)) {
				jobj.getAsJsonObject().remove("productType");
				isDF = true;
			}
		}
		
		return apiMappingUrlRepository.findByServiceCodeApplicationIdAndIsDf(serviceCode, applicationId, isDF );
	}
	
	public ApiMappingUrl urlAndvalidateTypeIsDFCallback(JsonObject jobj, String serviceCode){
		boolean isDF = false;
		
/**** next step untuk yg sudah di mapping *****/
		
//		String productType  = jobj.get("productType").getAsString();
//		String codeType = new String();
//		
//		if(productType.equals(AppsUtil.Product_type_DF)) {
//			codeType = jobj.get("comm_code").getAsString();
//			isDF = true;
//		}else {
//			if(serviceCode.equals(AppsUtil.FreezeLimit)) {
//				codeType = jobj.get("ro_code").getAsString();
//			}else {
//				codeType = jobj.get("member_code").getAsString();
//			}
//			
//		}
//		return apiMappingUrlRepository.findByServiceCodeAndCodeTypeAndIsDf(serviceCode, codeType, isDF);
		
		String codeType = jobj.get("comm_code").getAsString();
		
		return apiMappingUrlRepository.findByServiceCodeAndCodeTypeAndIsDf(serviceCode, codeType, isDF);
		
	}
	
	
	public void printHeaderLoggerIN(String accessTime, String applicationId, String authSignature, String serviceCode,AppRequest appRequest) throws JsonProcessingException {
		LOGGER.info("::::::::::::::LOGGER IN START::::::::::::::");
		LOGGER.info(" ");
		LOGGER.info("[serviceCode] [in]   : " + serviceCode);
		LOGGER.info("[accessTime] [in]    : " + accessTime);
		LOGGER.info("[applicationId] [in] : " + applicationId);
		LOGGER.info("[authSignature] [in] : " + authSignature);
		LOGGER.info("[REQUEST] [in]       : " + objectMapper.writeValueAsString(appRequest));
		LOGGER.info(" ");
		LOGGER.info("::::::::::::::LOGGER IN END::::::::::::::::");
	}
	
}
