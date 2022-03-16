package id.co.bsi.scm.h2h.api.rest.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Data
public class RequestBodySendInvoice {
	
	@NonNull
	private String actionType ;
	@NonNull
	private String invoiceAmt ;
	@NonNull
	private String invoiceCcy  ;
	@NonNull
	private String invoiceNo ;
	@NonNull
	private String invoiceDate ;
	@NonNull
	private String invoiceDueDate ;
	@NonNull
	private String sellerCode ;
	@NonNull
	private String profileCode ;
	@NonNull
	private String buyerCode ;
	@NonNull
	private String contractNo ;
	
}
