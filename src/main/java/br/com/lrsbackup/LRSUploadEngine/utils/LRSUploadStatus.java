package br.com.lrsbackup.LRSUploadEngine.utils;

import org.springframework.web.client.RestTemplate;
import br.com.lrsbackup.LRSManager.enums.LRSOptionsFileStatus;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSQueueFileForm;
import br.com.lrsbackup.LRSManager.services.model.LRSQueueFileServiceModel;
import br.com.lrsbackup.LRSUploadEngine.services.model.LRSUploadFileForm;

public class LRSUploadStatus {

	public void changeFileStatusToUploading(LRSUploadFileForm fileUploaded, LRSOptionsFileStatus status) {
		
		String cBaseURILRSManager = new LRSManagerAddress().getLRSManagerURI();
		
		
		LRSQueueFileForm fileFormUploading = new LRSQueueFileForm();
		fileFormUploading.setCloudProvider(fileUploaded.getPublicCloud());
		fileFormUploading.setOriginalfullname(fileUploaded.getOriginalFileName());
		fileFormUploading.setStatus(status.toString());
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject(cBaseURILRSManager.concat("/queue/v1/updatestatus"), fileFormUploading, LRSQueueFileServiceModel.class);
		
	}
}
