package com.github.vincemann.posting.service;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class LicenseService {
////
////	@Autowired
////	MessageSource messages;
////
////	@Autowired
////	private LicenseRepository licenseRepository;
////
////	@Autowired
////	CommercialProperties config;
////
////
////	public License getLicense(String licenseId, String organizationId){
////		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
////		if (null == license) {
////			throw new IllegalArgumentException(String.format(messages.getMessage("license.search.error.message", null, null),licenseId, organizationId));
////		}
////		return license.withComment(config.getText());
////	}
////
////	public License createLicense(License license){
////		license.setLicenseId(UUID.randomUUID().toString());
////		licenseRepository.save(license);
////
////		return license.withComment(config.getText());
////	}
////
////	public License updateLicense(License license){
////		licenseRepository.save(license);
////
////		return license.withComment(config.getText());
////	}
////
////	public String deleteLicense(String licenseId){
////		String responseMessage = null;
////		License license = new License();
////		license.setLicenseId(licenseId);
////		licenseRepository.delete(license);
////		responseMessage = String.format(messages.getMessage("license.delete.message", null, null),licenseId);
////		return responseMessage;
////
////	}
//}
