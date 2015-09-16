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
https://www.daniweb.com/web-development/php/threads/481885/import-csv-pdo-msql*/
session_start(); 
//echo $_SESSION['filename'];
echo "File Contents----->";
echo "<br/>";
echo "<br/>";
echo "<br>";
$fileName = $_SESSION['filename'];
$filecontents = file_get_contents($fileName);
$file="new".basename($fileName);
$start_time = microtime(true);
header("Cache-Control: public");
header("Content-Description: File Transfer");
header("Content-Disposition: attachment; filename=$file");
header("Content-Type: application/zip");
//header("Content-Transfer-Encoding: binary");
readfile($fileName);
$end_time = microtime(true);
$time = ($start_time - $end_time)/60;
echo "<br/>";
echo "<br/>";
echo "Time ----------".$time." Seconds";
?>