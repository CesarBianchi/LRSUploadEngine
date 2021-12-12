package br.com.lrsbackup.LRSUploadEngine.utils;


import java.time.LocalDateTime;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import br.com.lrsbackup.LRSManager.enums.LRSOptionsFileStatus;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSQueueFileForm;
import br.com.lrsbackup.LRSManager.services.model.LRSQueueFileServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSUploadFileForm;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;

public class LRSUploadStatus {

	public void changeFileStatusToUploading(LRSUploadFileForm fileUploaded, LRSOptionsFileStatus status) {
		
		changeStatusFileRequest(fileUploaded,status);
		return;
				
	}
	
	private void changeStatusFileRequest(LRSUploadFileForm fileUploaded, LRSOptionsFileStatus status) {
		String cBaseURILRSManager = new LRSManagerAddress().getLRSManagerURI();
		RestTemplate restTemp = new RestTemplate();	

		LRSQueueFileForm fileFormUploading = new LRSQueueFileForm();
		fileFormUploading.setCloudProvider(fileUploaded.getPublicCloud());
		fileFormUploading.setOriginalfullname(fileUploaded.getOriginalFileName()); 
		fileFormUploading.setCloudProvider(fileUploaded.getPublicCloud());
		fileFormUploading.setProcessedDate(LocalDateTime.now());
		fileFormUploading.setStatus(status.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LRSQueueFileForm> entity = new HttpEntity<LRSQueueFileForm>(fileFormUploading ,headers);

			
		try {
			restTemp.put(cBaseURILRSManager.concat("/queue/v1/updatestatus"), entity);
			new LRSConsoleOut("File ".concat(fileUploaded.getOriginalFileName()).concat(" successfully changed to ".concat(status.toString())));
		
		} catch(Exception e) {
			new LRSConsoleOut("Falha ao tentar alterar Status do Arquivo.");
			new LRSConsoleOut(e);
			
		}
		
	
		
	}
	
}
