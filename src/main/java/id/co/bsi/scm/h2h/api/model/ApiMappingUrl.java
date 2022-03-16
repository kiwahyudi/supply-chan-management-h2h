package id.co.bsi.scm.h2h.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "api_mapping_url", schema = "public")
public class ApiMappingUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "codeType")
    private String codeType;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "applicationId")
    private String applicationId;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "isDf")
    private boolean isDf;
    
    @Column(name = "serviceCode")
    private String serviceCode;
}
