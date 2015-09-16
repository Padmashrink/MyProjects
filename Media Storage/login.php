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
https://www.daniweb.com/web-development/php/threads/481885/import-csv-pdo-msql */
session_start();
?>
<?php
    error_reporting(E_ALL);
    ini_set('display_errors','Off');
    try {
     $dbh = new pdo( 'mysql:unix_socket=/cloudsql/******:******;dbname=cloudproject',
    'root',
    ''
    );
    if(isset($_POST['login'])) {
        if(isset($_POST['username']) && isset($_POST['password'])){
             $myusername=$_POST['username'];
             $mypassword=$_POST['password'];
             $mypassword1=md5($mypassword);
             $stmt = $dbh->prepare("select name, password from cloudproject.cloud1 where name='$myusername' and password='$mypassword1'");
             $stmt->execute();
             $row = $stmt->fetchAll(); 
             $count1 = count($row);
             if($count1==1)
             { 
      		        foreach($row as $item){
			                  $_SESSION['username1'] = $item['name'];
			                  header("location: upload.php");
                 	            return;			 
             	    } 
             }
             else
             {
  		             echo "You have entered incorrect login";
             } 
             $dbh->commit();
        }
    }
    }
    catch (PDOException $e) {
     print "Error!: " . $e->getMessage() . "<br/>";
     die();
} 
?>
<html>
<head><title>Login</title>
</head>
<body bgcolor="OOFF99">
	<center>
		<h1> Login here! </h1>
        <form method="POST" action="login.php">
		Username:<br>
		<input type="text" name="username"/>
		<br/>
		Password:<br>
		<input type="password" name="password"/>
		<br/>
        <input type="submit" value="Login" name="login"/>
		</form>
    </center>
</body>
</html>
