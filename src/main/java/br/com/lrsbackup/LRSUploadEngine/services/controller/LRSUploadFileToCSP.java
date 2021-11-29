package br.com.lrsbackup.LRSUploadEngine.services.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lrsbackup.LRSManager.enums.LRSOptionsCloudProvider;
import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSRequestConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSRequestIDGenerator;
import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;
import br.com.lrsbackup.LRSUploadEngine.services.model.LRSUploadFileForm;
import br.com.lrsbackup.LRSUploadEngine.services.model.LRSUploadFileFormDAO;
import br.com.lrsbackup.LRSUploadEngine.services.model.LRSUploadFileServiceModel;

@RestController
public class LRSUploadFileToCSP {

	
	private HttpStatus finalHttpStatus;
	private LRSApplicationVersion appDetails = new LRSApplicationVersion();
	private LRSRequestConsoleOut requestConsoleOut = new LRSRequestConsoleOut();
	private LRSResponseInfo responseInfo = new LRSResponseInfo();
	
	
	public LRSUploadFileToCSP() {
		super();
		this.responseInfo.setAppName(appDetails.getApplicationName());
		this.responseInfo.setServiceName(appDetails.getServiceName());
		this.responseInfo.setServiceVersion(appDetails.getServiceVersion());
	}

	@RequestMapping(value = "LRSUploadEngine/upload/v1/uploadfile", method = RequestMethod.POST)
	public ResponseEntity uploadfile(HttpServletRequest request,  @RequestBody LRSUploadFileForm pFile) {
		LRSUploadFileServiceModel response = new LRSUploadFileServiceModel();
		LRSResponseMessages messages = new LRSResponseMessages();
		setRespInfoInitialData(request);
		
		//1* Check if all mandatory fields isn't empty
		if (pFile != null) {
			
			//1.1 - Original File Name
			if (!pFile.getOriginalFileName().isEmpty()) {
				
				//1.2 - Destination File Name
				if (!pFile.getDestinationFileName().isEmpty()) {
					
					//1.3 - Public Cloud
					if (this.validPublicCloud(pFile.getPublicCloud())) {
						
						//1.4 - User and Password
						if ((!pFile.getCspUserName().isEmpty()) && (!pFile.getCspUserKey().isEmpty()) ) {
						
							//Call uploadFunction. MultiThread!!!!!
							//TODO TODO TODO
							
							
							finalHttpStatus = HttpStatus.OK;
							messages.addMessage("The file ".concat(pFile.getOriginalFileName()).concat(" succesfully joined to upload in ".concat(pFile.getPublicCloud())));
						} else {
							finalHttpStatus = HttpStatus.BAD_REQUEST;
							messages.addMessage("cloudUser or CloudUser Key is invalid. Please, check it and try again!");
						}
						
					} else {
						finalHttpStatus = HttpStatus.BAD_REQUEST;
						messages.addMessage("Public Cloud is invalid. Please, check it and try again!");
					}
					
				} else {
					finalHttpStatus = HttpStatus.BAD_REQUEST;
					messages.addMessage("Destination Path is invalid. Please, check it and try again!");
				}
								
			} else {
				finalHttpStatus = HttpStatus.BAD_REQUEST;
				messages.addMessage("OriginalFileName is invalid. Please, check it and try again!");
			}
				
		} else {
			finalHttpStatus = HttpStatus.BAD_REQUEST;
			messages.addMessage("One or more fields are invalid. Please, check it and try again!");
		}
		
		setRespInfoFootData(finalHttpStatus);
		LRSUploadFileFormDAO fileToUpload = new LRSUploadFileFormDAO().convert(pFile);
		response = new LRSUploadFileServiceModel(responseInfo,fileToUpload,messages);
		
		requestConsoleOut.println(request,response);
		return ResponseEntity.status(finalHttpStatus).body(response);	
	}

	
	
	private boolean validPublicCloud(String publicCloud) {
		boolean lRet = false;
		
		if (!publicCloud.isEmpty()) {
			
			if (publicCloud.trim().toUpperCase().equals(LRSOptionsCloudProvider.AWS.toString())) {
				lRet = true;
			}
			
			if (publicCloud.trim().toUpperCase().equals(LRSOptionsCloudProvider.AZURE.toString())) {
				lRet = true;
			}
			
			if (publicCloud.trim().toUpperCase().equals(LRSOptionsCloudProvider.ORACLE.toString())) {
				lRet = true;
			}
			
		}
		
		return lRet;
	}
	
	private void setRespInfoInitialData(HttpServletRequest request) {
		this.responseInfo.setRequestTime(java.time.LocalTime.now().toString().substring(0,12));
		this.responseInfo.setResourceName(request.getRequestURI());
		this.responseInfo.setClientIP(request.getRemoteAddr());
		
		return;
	}
	
	private void setRespInfoFootData(HttpStatus httpStat) {
		this.responseInfo.setHttpStatus(finalHttpStatus);
		this.responseInfo.setRequestID(new LRSRequestIDGenerator().getNewRequestID());
		this.responseInfo.setResponseTime(java.time.LocalTime.now().toString().substring(0,12));
	}
}
