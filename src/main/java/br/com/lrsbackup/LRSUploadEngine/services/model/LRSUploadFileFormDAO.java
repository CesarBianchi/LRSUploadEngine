package br.com.lrsbackup.LRSUploadEngine.services.model;

public class LRSUploadFileFormDAO {
	private String originalFileName = new String();
	private String destinationFileName = new String();
	private String publicCloud = new String();
	private String storageRepositoryName = new String();
	
	public LRSUploadFileFormDAO() {
		super();
	}

	public LRSUploadFileFormDAO(String originalFileName, String destinationFileName, String publicCloud, String storageName) {
		super();
		this.originalFileName = originalFileName;
		this.destinationFileName = destinationFileName;
		this.publicCloud = publicCloud;
		this.storageRepositoryName = storageName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getDestinationFileName() {
		return destinationFileName;
	}

	public void setDestinationFileName(String destinationFileName) {
		this.destinationFileName = destinationFileName;
	}

	public String getPublicCloud() {
		return publicCloud;
	}

	public void setPublicCloud(String publicCloud) {
		this.publicCloud = publicCloud;
	}
	
	public String getStorageRepositoryName() {
		return storageRepositoryName;
	}

	public void setStorageRepositoryName(String storageRepositoryName) {
		this.storageRepositoryName = storageRepositoryName;
	}

	public LRSUploadFileFormDAO convert(LRSUploadFileForm pFileFull) {
		
		LRSUploadFileFormDAO localDao = new LRSUploadFileFormDAO();
		
		localDao.setDestinationFileName(pFileFull.getDestinationFileName());
		localDao.setOriginalFileName(pFileFull.getOriginalFileName());
		localDao.setPublicCloud(pFileFull.getPublicCloud());
		localDao.setStorageRepositoryName(pFileFull.getStorageRepoName());
		
		return localDao;
	}
}