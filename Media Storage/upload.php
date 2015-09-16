<?php 
/*
References :
https://www.daniweb.com/web-development/php/threads/481885/import-csv-pdo-msql
https://www.daniweb.com/web-development/php/threads/481885/import-csv-pdo-msql
http://php.net/manual/en/function.readfile.php
http://stackoverflow.com/questions/24435602/google-app-engine-uploaded-files-not-public-on-google-cloud-storage?rq=1
http://php.net/manual/en/function.microtime.php
https://cloud.google.com/appengine/docs/php/googlestorage/public_access
https://cloud.google.com/appengine/docs/php/googlestorage/user_upload
https://cloud.google.com/appengine/docs/php/mail/
https://www.daniweb.com/web-development/php/threads/481885/import-csv-pdo-msql

*/
session_start();
error_reporting(E_ALL);
ini_set('display_errors','On');
 
require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;
$options = [ 'gs_bucket_name' => '******' ];
$upload_url = CloudStorageTools::createUploadUrl('/upload_handler.php', $options); 

$user=$_SESSION['username1'];
//echo $user; 
if(isset($_POST['logout'])){
      unset($_SESSION['username1']);
      session_destroy();
      header("location: startpage.php");
      return;
}
?>
<html>
<head>
<title>An application on Google App Engine</title>
</head>
<body bgcolor="OOFF99">
<center>
<h1>UPLOAD HERE!!</h1>
<form action="<?php echo $upload_url?>" method="POST" enctype="multipart/form-data">
<fieldset>
<legend> UPLOAD </legend>
<label>Upload the files here:
<input type="file" name="fileToUpload" id="fileToUpload"></label>
<br/>
Enter email address here:<br/>
<input type="text" name="email">
<br/>
<input type="submit" value="Upload" name="submit">
</form>
</fieldset>
<form action ="homescreen.php" method ="post">
<input type="submit" name="logout" value="Logout" />
</form>
</center>
</body>
</html>