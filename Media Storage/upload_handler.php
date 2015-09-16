<?php
session_start();   
require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;
use \google\appengine\api\mail\Message;
 
$fileName = 'gs://*******/'.$_FILES['fileToUpload']['name'];
$email = $_POST['email'];
//echo $email;
 
$_SESSION['filename'] = $fileName;
$user=$_SESSION['username1'];
//echo $_SESSION['filename'];
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
 
$string_to_email = "Name of the file----------".$name_file."    Type of the file---------- ".$type_file."       Size of the file---------- ".$size_file."        Time ----------".$time." Seconds";
//echo $string_to_email;
echo "Name of the File - ".$name_file;
echo "<br/>";
echo "Type of the file - ".$type_file;
echo "</br>";
echo "Size of the file - ".$size_file;
echo "<br/>";
echo "Time".$time." Seconds";
echo "<br/>";
echo "<br/>";
 
try
{
  $mail = new Message();
  $mail->setSender("padmashrink@gmail.com");
  $mail->addTo($email);
  $mail->setSubject("Email about uploaded file on Google App Engine");
  $mail->setTextBody("File Information=>      ".$string_to_email);
  $mail->send();
} catch (InvalidArgumentException $e) {
        echo ("Unable to send the message");
}
 
 
try{
    $dbh = new pdo( 'mysql:unix_socket=/cloudsql/*******:******;dbname=cloudproject',
    'root',
    ''
    );
    $csv = $fileName;
    $csvname = basename($csv);
    
    $stmt1 = $dbh->prepare("insert into cloudproject.cloud2 values('$csvname','$user')");
    $stmt1->execute();
    
    $stmt2 = $dbh->prepare("select fname from cloudproject.cloud2");
    $stmt2->execute();
  
    while($row = $stmt2->fetch(PDO::FETCH_ASSOC)){
        echo "http://storage.googleapis.com/******/".$row['fname'];
        echo "<br/>";
    }  
    $start_time1 = microtime(true);
    $csv = "gs://cloud06/".$csvname;
    
    $column0 = 'user'; 
    $column1 = 'time2'; 

    $csv_file = $csv;
    $csvfile = fopen($csv_file, 'r');
    $theData = fgets($csvfile);
    $i = 0;
    while (!feof($csvfile)) {
        $csv_data[] = fgets($csvfile);
        $csv_array = explode(",", $csv_data[$i]);
        $insert_csv = array();  
        $insert_csv[$column0] = $csv_array[0]; 
        $insert_csv[$column1] = $csv_array[1];
   
        $stmt3 = $dbh->prepare("insert into cloudproject.data values('$insert_csv[$column0]','$insert_csv[$column1]')");
        $stmt3->execute();
        $i++;
    } 
    $end_time1 = microtime(true);
 
    $time1 = ($start_time - $end_time)/60;   
    echo "<br/>";
    echo "<br/>";
    echo "Time to upload to database: ".$time1." Seconds";
}
catch (PDOException $e) {
     print "Error!: " . $e->getMessage() . "<br/>";
     die();
}
?>
<html>
<body bgcolor="OOFF99"> 
    <form action="http://******.appspot.com/upload.php" method="post">
        <input type="submit" name="back" value="Back" />
    </form>
    <br/>
    <br/>
    <a href="http://helloworldpadmashri.appspot.com/delete.php">Access Here!</a>
    <br/>
    <br/>
    <br/>
    <a href="http://storage.googleapis.com/cloud06/<?php echo $name_file;?>"> Download Here!</a>
</body>
</html>