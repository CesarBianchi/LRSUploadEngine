package br.com.lrsbackup.LRSUploadEngine.cspengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSOperationalSystem;
import br.com.lrsbackup.LRSUploadEngine.services.controller.LRSCloudCredentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class LRSCSPEngineAWS {
	
	private LRSCloudCredentials cspCredentials = new LRSCloudCredentials();
	private String originalFileName = new String();
	private String defAWSRegion = new String();
	private String destinationPath = new String();
	private String bucketName = new String();
	
	public LRSCloudCredentials getCspCredentials() {
		return cspCredentials;
	}

	public void setCspCredentials(LRSCloudCredentials cspCredentials) {
		this.cspCredentials = cspCredentials;
	} 

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getDefAWSRegion() {
		return defAWSRegion;
	}

	public void setDefAWSRegion(String defAWSRegion) {
		this.defAWSRegion = defAWSRegion;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String uploadFileToCloud() {
		String fileToUpload = this.destinationPath.substring(1,this.destinationPath.length());
		Region region = Region.US_EAST_2;

	
		
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(cspCredentials.getAccessKey(),cspCredentials.getSecretKey());
		S3Client s3 = S3Client.builder()
        		.region(region)
        		.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
        		.build();
        
        try {

            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "LRSBackup Application");

        	byte[] objectFile = new LRSOperationalSystem().getObjectFile(this.originalFileName);
            
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(this.bucketName)
                    .key(fileToUpload)
                    .metadata(metadata)
                    .build();

            
            PutObjectResponse response = s3.putObject(putOb, RequestBody.fromBytes(objectFile));
            new LRSConsoleOut(response.eTag());
            
            return response.eTag();

        } catch (S3Exception e) {
            new LRSConsoleOut("ERROR WHILE TRYING UPLOAD FILE ".concat(originalFileName).concat(" to AWS S3 Bucket"));
        	System.err.println(e.getMessage());
            System.exit(1);
        }
        return "";
        
	}


	
}
