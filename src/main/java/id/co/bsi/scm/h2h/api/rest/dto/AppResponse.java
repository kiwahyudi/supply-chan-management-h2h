package id.co.bsi.scm.h2h.api.rest.dto;

/**
 * Author   : kw
 * Date     : 3/1/22 03:03 PM
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AppResponse {
    private Object bodyresponse;
}

