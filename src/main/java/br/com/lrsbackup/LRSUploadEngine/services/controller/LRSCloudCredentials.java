package br.com.lrsbackup.LRSUploadEngine.services.controller;

public class LRSCloudCredentials {
	
	private String accessKey = new String();
	private String secretKey = new String();

	public LRSCloudCredentials() {
		super();
	}
	
	public LRSCloudCredentials(String accessKey, String secretKey) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
			
}
