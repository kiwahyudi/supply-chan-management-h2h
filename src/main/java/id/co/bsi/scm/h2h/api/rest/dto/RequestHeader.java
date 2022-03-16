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
public class RequestHeader {
	@NonNull
	private String siteCode;
	// private String accessCode;
	private String referenceNo;
}
