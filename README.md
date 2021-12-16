# LRSUploadEngine

LRSUploadEngine is a part of LRSBackup Solution, responsible to perform upload files and make all comunications with cloud public providers, as Amazon AWS, Azure and Oracle Cloud.
Important Notice: Coming soon, I'll add Google Cloud Storage also. Nowadays, just Amazon AWS are fully avaiable.

## Getting Started (for developers)
All code about LRSUploadEngine are available in **/src** directory and all libraries dependency are available in pom.xml file.
You can find a main class to run this program in **/src/br/com/lrsbackup/LRSUploadEngine/LRSUploadEngineApplication.java**

Important Notice: This project is under development, almost done. So, there are some bugs yet and some values/parameters "hardcoded".  I'm working over it, so please, be patience!
All codes were write using Eclipse IDE using a "Maven Project"


## Getting Started (for end-users)
To run LRSUploadEngine, look at the binary file (executable file) about LRSUploadEngine. 
After download it, plese run the following commands in your command line application (Terminal to MacOs/Linux users or CMD to Windows Users)
```
java -jar -Dserver.port=6003 LRSUploadEngine.jar
```
We strongly recommend that you use a Docker container to run LRSManager. In this case, all docker containers related to LRSBackup environment needs have the same file mount point as a "Protected Files Source"
By the way, the LRSManager runs over 6003 HTTP Port.

## Authors
- [Cesar Bianchi](https://www.linkedin.com/in/cesar-bianchi-9b90571b/), since 2021.

## License
 This software is licensed under [GPL-3.0 License](https://www.gnu.org/licenses/gpl-3.0.pt-br.html)   

## More Info
 Please visit http://www.lrsbackup.com (under construction) or write to me: cesar_bianchi@hotmail.com
 
 
