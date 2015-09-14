
<html>
<body>
<?php
error_reporting(E_ALL);
ini_set('display_errors','On');
try {
  $dbname = dirname($_SERVER["SCRIPT_FILENAME"]) . "/mydb.sqlite";
  $dbh = new PDO("sqlite:$dbname");
  $dbh->beginTransaction();
  if(isset($_POST['username'])&& ($_POST['password'])&&($_POST['fullname'])&&($_POST['email'])){
     if(isset($_POST['Register1'])){
         $myusername1=$_POST['username'];
         $mypassword1=$_POST['password'];
         $mypassword11=md5($mypassword1);
         $myfullname1=$_POST['fullname'];
         $email1=$_POST['email'];
         $stmt1 = $dbh->prepare("select * from users where username = '".$myusername1."'");
         $stmt1->execute();
         $rows = $stmt1->fetchAll();
         $count2 = count($rows);
         if($count2 == 0){
             $dbh->exec("insert into users values('$myusername1','$mypassword11','$myfullname1','$email1')")
               or die(print_r($dbh->errorInfo(), true));
			 $stmt = $dbh->prepare("select * from users");
             $stmt->execute(); 
             $row1 = $stmt->fetchAll();
             $dbh->commit();
			 header("location: board.php");
             return;
            }
         else
        {
        echo "user already exists";
        }
	 }
  }
}
catch (PDOException $e) {
     print "Error!: " . $e->getMessage() . "<br/>";
     die();
}  
?>
<form action ="" method ="POST">
<label>username <input type="text" name="username"/></label>
<label>password <input type="password" name="password"/></label>
<label>fullname <input type="text" name="fullname"/></label>
<label>email <input type="text" name="email"/></label>
<input type="submit" name="Register1" value="register" />
</form>
</body>
</html>