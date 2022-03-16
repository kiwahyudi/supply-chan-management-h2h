package id.co.bsi.scm.h2h.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.bsi.scm.h2h.api.model.ApiMappingUrl;

@Repository
public interface ApiMappingUrlRepository extends JpaRepository<ApiMappingUrl, Long> {
	
	@Query(value = "select a from ApiMappingUrl a where a.codeType = :codeType", nativeQuery = false)
	ApiMappingUrl findByCodeType(@Param("codeType") String codeType);
	
	@Query(value = "select a from ApiMappingUrl a where a.codeType = :codeType and a.applicationId = :appId", nativeQuery = false)
	ApiMappingUrl findByCodeTypeAndApplicationId(@Param("codeType") String codeType, @Param("appId")  String appId);
	
	@Query(value = "select a from ApiMappingUrl a where a.codeType = :codeType and a.applicationId = :appId and isDf = :isdf", nativeQuery = false)
	ApiMappingUrl findByCodeTypeApplicationIdAndIsDf(@Param("codeType") String codeType, @Param("appId")  String appId, @Param("isdf")  boolean isDf);
	
	@Query(value = "select a from ApiMappingUrl a where a.serviceCode = :serviceCode and a.applicationId = :appId and isDf = :isdf", nativeQuery = false)
	ApiMappingUrl findByServiceCodeApplicationIdAndIsDf(@Param("serviceCode") String serviceCode, @Param("appId")  String appId, @Param("isdf")  boolean isDf);
	
	@Query(value = "select a from ApiMappingUrl a where a.codeType = :codeType and a.applicationId = :appId and isDf = :isdf and a.serviceCode = :serviceCode", nativeQuery = false)
	ApiMappingUrl findByCodeTypeApplicationIdServiceCodeAndIsDf(@Param("codeType") String codeType, @Param("appId")  String appId, @Param("isdf")  boolean isDf, @Param("serviceCode") String serviceCode);

	@Query(value = "select a from ApiMappingUrl a where a.serviceCode = :serviceCode and a.applicationId = :appId ", nativeQuery = false)
	ApiMappingUrl findByServiceCodeAndApplicationId(@Param("serviceCode") String serviceCode, @Param("appId")  String appId);
	
	@Query(value = "select a from ApiMappingUrl a where a.serviceCode = :serviceCode and a.codeType = :codeType and isDf = :isdf ", nativeQuery = false)
	ApiMappingUrl findByServiceCodeAndCodeTypeAndIsDf(@Param("serviceCode") String serviceCode, @Param("codeType") String codeType,@Param("isdf")  boolean isDf);
}
