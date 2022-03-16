package id.co.bsi.scm.h2h.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * Author   : kw
 * Date     : 3/1/22 03:03 PM
 */

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import id.co.bsi.scm.h2h.api.rest.dto.AppRequest;
import id.co.bsi.scm.h2h.api.servicesimpl.AutenticationUserServiceImpl;
import id.co.bsi.scm.h2h.api.util.AppsUtil;

@RestController	
@RequestMapping("/api/cmsh2h")
public class ScmH2HController {

	@Autowired
	private AutenticationUserServiceImpl autenticationUserService;

	/* API Provided by Customer */
    @PostMapping("/sendInvoice")
    public ResponseEntity<Object> sendInvoice(
    		@RequestHeader(value="accessTime", required=false) String accessTime,
    		@RequestHeader(value="applicationId", required=false) String applicationId,
    		@RequestHeader(value="authSignature", required=false) String authSignature,
    		@RequestBody AppRequest request
    		) throws JsonMappingException, JsonProcessingException {
    	
    	return autenticationUserService.validateHeader(accessTime, 
    			applicationId, authSignature, request, 
    			AppsUtil.sendInvoice);
    }
    
    /* API Provided by Customer */
    @PostMapping("/TxnInquiry")
    public ResponseEntity<Object> txnInquiry(
    		@RequestHeader(value="accessTime", required=false) String accessTime,
    		@RequestHeader(value="applicationId", required=false) String applicationId,
    		@RequestHeader(value="authSignature", required=false) String authSignature,
    		@RequestBody AppRequest request
    		) throws JsonMappingException, JsonProcessingException {
    	
    	return autenticationUserService.validateHeader(accessTime, 
    			applicationId, authSignature, request, 
    			AppsUtil.TxnInquiry);
    }
    
	
    /* API Provided by Customer */
    @PostMapping("/LimitInquiry")
    public ResponseEntity<Object> limitInquiry(
    		@RequestHeader(value="accessTime", required=false) String accessTime,
    		@RequestHeader(value="applicationId", required=false) String applicationId,
    		@RequestHeader(value="authSignature", required=false) String authSignature,
    		@RequestBody AppRequest request) throws JsonMappingException, JsonProcessingException {
    	
    	return autenticationUserService.validateHeader(accessTime, 
    			applicationId, authSignature, request, 
    			AppsUtil.LimitInquiry);
    }
    
    /* API Provided by DVC */
    @PostMapping("/FinanceDisbursementFlag")
    public ResponseEntity<Object> financeDisbursementFlag(
    		@RequestBody AppRequest request) throws JsonMappingException, JsonProcessingException  {
    	return autenticationUserService.validateCallBack(request, AppsUtil.FinanceDisbursementFlag);
    }
    
    /* API Provided by DVC */
    @PostMapping("/FinanceRepaymentInfo")
    public ResponseEntity<Object> financeRepaymentInfo(
    		@RequestBody AppRequest request) throws JsonMappingException, JsonProcessingException {
    	return autenticationUserService.validateCallBack(request, AppsUtil.FinanceRepaymentInfo);
    }
    
    /* API Provided by DVC */
    @PostMapping("/FreezeLimit")
    public ResponseEntity<Object> freezeLimit(
    		@RequestBody AppRequest request) throws JsonMappingException, JsonProcessingException {
    	return autenticationUserService.validateCallBack(request, AppsUtil.FreezeLimit);
    }
}
