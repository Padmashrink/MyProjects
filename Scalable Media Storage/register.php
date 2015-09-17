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
try{
    $dbh = new pdo( 'mysql:unix_socket=/cloudsql/******:******;dbname=cloudproject',
    'root',
    ''
    );

  
   $dbh->beginTransaction();
   
     if(isset($_POST['Register1'])){
         if(isset($_POST['username']) && $_POST['password']){
         $myusername1=$_POST['username'];
         $mypassword1=$_POST['password'];
         $mypassword11=md5($mypassword1);
         $stmt1 = $dbh->prepare("select username from cloudproject.cloud1 where username = '$myusername1'");
         $stmt1->execute();
         $rows = $stmt1->fetchAll();
         $count2 = count($rows);
         if($count2 == 0){
             $stmt2 = $dbh->prepare("insert into cloudproject.cloud1 values('$myusername1','$mypassword11')");
             $stmt2->execute();
             header("location: homescreen.php");
        }
        else{
             echo "User already exists";
         }
      }
  }
  $dbh->commit();
}
catch (PDOException $e) {
     print "Error!: " . $e->getMessage() . "<br/>";
     die();
}
?>
<html>
<head>
	<title>Register</title>
<body bgcolor="OOFF99">
	<center>
		<h1> Sign Up here! </h1>
		<form action="register.php" method="post">
		Username:<br>
		<input type="text" name="username">
		<br>
		Password:<br>
		<input type="password" name="password">
		<br>
		<input type="submit" name="Register1" value="Submit">
		</form>
	</center>
</body>
</html>
