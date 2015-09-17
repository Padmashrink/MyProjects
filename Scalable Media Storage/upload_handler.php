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
require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;
use \google\appengine\api\mail\Message;
 
$fileName = 'gs://cloud07/'.$_FILES['fileToUpload']['name'];

$_SESSION['filename'] = $fileName;
$user=$_SESSION['username1'];

$options = array('gs'=>array('acl'=>'public-read','Content-Type' => $_FILES['fileToUpload']['type']));
$start_time = microtime(true);
 
$context = stream_context_create($options);
$end_time = microtime(true);
 
$time = ($start_time - $end_time)/60;
 
 
if (false == rename($_FILES['fileToUpload']['tmp_name'], $fileName, $context)) {
  die('Could not rename.');
}
 
$object_url = CloudStorageTools::getPublicUrl($fileName, true);
 
$data = $_FILES['fileToUpload'];
$name_file = $data['name'];
$type_file = $data['type'];
$size_file = $data['size'];
 
try{
    $dbh = new pdo( 'mysql:unix_socket=/cloudsql/******:******;dbname=cloudproject',
    'root',
    ''
    );
    $csv = $fileName;
    $csvname = basename($csv);
    
    $stmt1 = $dbh->prepare("insert into cloudproject.cloud2 values('$csvname','$user')");
    $stmt1->execute();
    
    $stmt2 = $dbh->prepare("select fname from cloudproject.cloud2");
    $stmt2->execute();
    ?>
    <table border="1">
    <?php
    while($row = $stmt2->fetch(PDO::FETCH_ASSOC)){
        ?>
        <tr><td><?php echo "http://storage.googleapis.com/*******/".$row['fname'];?></td>
        <?php 
        //echo "<br/>";
        $fn = $row['fname'];
        $arr=split('\.',$fn);                                                                                       
        ?>
           <td><img src="http://storage.googleapis.com/******/<?php echo $fn;?>"/></td></tr>
        <?php    
    }  
    ?>
    </table>
    <?php
}
catch (PDOException $e) {
     print "Error!: " . $e->getMessage() . "<br/>";
     die();
}
?>
<html>
<body bgcolor="OOFF99"> 
    <br/>
    <form action="http://helloworldpadmashri1.appspot.com/upload.php" method="post">
        <input type="submit" name="back" value="Back" />
    </form>
    <br/>
</body>
</html>