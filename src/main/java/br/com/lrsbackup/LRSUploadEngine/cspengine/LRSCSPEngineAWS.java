package br.com.lrsbackup.LRSUploadEngine.cspengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSOperationalSystem;
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

	public String uploadFileToCloud() {
		String fileToUpload = originalFileName.substring(1,originalFileName.length());
		Region region = Region.US_EAST_2;

		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(cspCredentials.getAccessKey(),cspCredentials.getSecretKey());
		S3Client s3 = S3Client.builder()
        		.region(region)
        		.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
        		.build();
        
        try {

            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "LRSBackup Application");

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket("myfreezefiles") //TODO - PLEASE hard coded NOOOOO Mr Cesar!
                    .key(fileToUpload)
                    .metadata(metadata)
                    .build();

            
            PutObjectResponse response = s3.putObject(putOb, RequestBody.fromBytes(getObjectFile(originalFileName)));
            new LRSConsoleOut(response.eTag());
            
            return response.eTag();

        } catch (S3Exception e) {
            new LRSConsoleOut("ERROR WHILE TRYING UPLOAD FILE ".concat(originalFileName).concat(" to AWS S3 Bucket"));
        	System.err.println(e.getMessage());
            System.exit(1);
        }
        return "";
        
	}
	
	private String getOnlyFileName(String originalFileName) {
		String delimiter = new String();
		String onlyFileName = new String();
		LRSOperationalSystem mySO = new LRSOperationalSystem();
		
		if (mySO.isWindows()) {
			delimiter = "\\";
		} else {
			delimiter = "/";
		}
		
		int initialPoint = originalFileName.lastIndexOf(delimiter);
		int finalPoint = originalFileName.length();
		
		onlyFileName = originalFileName.substring(initialPoint,finalPoint);	
		onlyFileName = onlyFileName.replace(delimiter,"");
		
		return onlyFileName;
	}

	private static byte[] getObjectFile(String filePath) {

	        FileInputStream fileInputStream = null;
	        byte[] bytesArray = null;

	        try {
	            File file = new File(filePath);
	            bytesArray = new byte[(int) file.length()];
	            fileInputStream = new FileInputStream(file);
	            fileInputStream.read(bytesArray);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fileInputStream != null) {
	                try {
	                    fileInputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return bytesArray;
	    }
	
}
