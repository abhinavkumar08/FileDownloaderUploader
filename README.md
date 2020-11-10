# FileDownloaderUploader
The Project aims at downloading and uploading of any file to a remote SFTP, FTP, FTPS location
------------------------------------------------DATABASE SETUP-----------------------------------------------

install mysql 5.7.19 give the username as root and note the password.
navigate to the bin directory where mysql is installed and run the below command
./mysql -u root -p
Enter password which you set at the time of instllation.


Now run the below command to create database and table inside the database just created.

create database download;
use download;


create table download.FILE_DOWNLOAD_HISTORY
(
DOWNLOAD_ID VARCHAR(200) NOT NULL,
FILE_SOURCE VARCHAR(200) NOT NULL,
FILE_DESTINATION VARCHAR(200) NOT NULL,
DOWNLOAD_START_TIME DATETIME NOT NULL,
DOWNLOAD_END_TIME DATETIME DEFAULT NULL,
STATUS VARCHAR(50) NOT NULL,
PROTOCOL VARCHAR(10) NOT NULL,
DATA_SIZE VARCHAR(10) DEFAULT NULL,
DOWNLOAD_SPEED VARCHAR(10) DEFAULT NULL,
PRIMARY KEY (DOWNLOAD_ID)

);


create table download.FILE_DOWNLOAD_STATS
(
FILE_SOURCE VARCHAR(200) NOT NULL,
DOWNLOAD_SUCCESS_COUNT INT NOT NULL,
DOWNLOAD_FAILED_COUNT INT NOT NULL,
DOWNLOAD_FAILURE_PERCENTAGE DOUBLE NOT NULL,
PRIMARY KEY (FILE_SOURCE)
);

---------------------------------------------------File Downloader Setup -------------------------------------------


* Install and open IDE eclipse Neon 3 and above.

* Installing maven : If your eclipse does not come up with integrated maven plug in, download Maven Integration for eclipse by going to Help -> Eclipse Marketplace ( I used 3.3.9 for my development.)

* Create a workspace where you want to import the project .( File - > switch workspace -> others, Give directory name and Click ok).

* Unzip the FileDownloadSystem.zip and extract it to a location(X) on your hard disk.

* Importing Project : Click on File Tab - > Import - > Select on Maven drop drop down - > Existing maven project - > next - > Select root directory as X inside which there should be a pom.xml -> Finish.

* Once you click finish it will install all dependencies. Please check the console tab in eclipse to make sure there are no errors.

* Open the configuration file present in src/main/resources fileDownloadSystem.properties and fill the configuration values as per your machine and Database.

* Open the file : App.java present in src/main/java/com/agoda/downloader/execute - > Right click - > Run as java application.

* Provide input to the console in the form of : <URL1>, <URL2>, ...... ,<URLn>

* Check the file path mentioned in configuration to see the file being downloaded.

* Check the database for entries.
 


----------------------------------------------------React App Setup --------------------------------------------------


* Download Node server(it generally comes with node and npm package) from the url : https://nodejs.org/en/download/

* Cross check if the download happened successfully by running the command node -v and npm -v, it would display the version on the terminal.

* Unzip the DownloadStatus.zip and extract it to a specific location.

* Once extracted navigate to the DownloadStatus using terminal, you will see server.js file.

* Run the command node server.js ( This will start node web server on localhost port 5000, Also make sure that mysql service is running before running this command. )

* Paste this in the browser to perform a healthcheck : http://localhost:5000/api/healthcheck, 

* Navigate to client directory and run the command npm start to start the dev server.( This will deploy the application on localhost dev server, port 3000).

* Open the browser and paste the url http://localhost:3000 to see the list of downloads.

-------------------------------------------------------------------------------------------------------------------------
