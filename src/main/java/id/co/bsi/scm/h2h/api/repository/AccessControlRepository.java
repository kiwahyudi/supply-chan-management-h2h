package id.co.bsi.scm.h2h.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.bsi.scm.h2h.api.model.AccessControl;

@Repository
public interface AccessControlRepository extends JpaRepository<AccessControl, Long> {
	
	@Query(value = "select a from AccessControl a where a.applicationId = :appId ", nativeQuery = false)
	public AccessControl findByApplicationId(@Param("appId") String appId);
	
	@Query(value = "select a from AccessControl a where a.name = :name ", nativeQuery = false)
	public AccessControl findByName(@Param("name") String name);
}
