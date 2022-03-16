package id.co.bsi.scm.h2h.api.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppRequest {
    private RequestHeader requestHeader;
    private Object requestData;
}

