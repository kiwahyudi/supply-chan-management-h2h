package id.co.bsi.scm.h2h.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "access_control", schema = "public")
public class AccessControl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    
    @NotNull
    @Column(name = "key")
    private String key;

    @NotNull
    @Column(name = "secret")
    private String secret;

    @Column(name = "description")
    private String description;
    
    @Column(name = "applicationId")
    private String applicationId;
}
