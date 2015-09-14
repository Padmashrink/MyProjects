
<?php
session_start(); 
?>
<html>
<head><title>Message Board</title></head>
<body>
<?php
error_reporting(E_ALL);
ini_set('display_errors','On');
try {
  $dbname = dirname($_SERVER["SCRIPT_FILENAME"]) . "/mydb.sqlite";
  $dbh = new PDO("sqlite:$dbname");
  $dbh->beginTransaction();
  if(isset($_POST['username']) && ($_POST['password'])){
  if(isset($_POST['login'])){
         $myusername=$_POST['username'];
         $mypassword=$_POST['password'];
		 $mypassword1=md5($mypassword);
         $stmt = $dbh->prepare("select * from users where username = '".$myusername."' AND password = '".$mypassword1. "'");
         $stmt->execute();
		 $row = $stmt->fetchAll(); 
		 $count1 = count($row);
		 if($count1==1)
		 { 
		     foreach($row as $item){
			     $_SESSION['username1'] = $item['username'];
                 header("location: board1.php");
                 return;			 
             } 
         }
		 else
		 {
		     echo "you have entered incorrect login";
		 } 
         $dbh->commit();
     }}
	 if(isset($_POST['Register'])){
	 header("location: register.php");
	 return;
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
<input type="submit" name="login" value="login" />
<input type="submit" name="Register" value="register" />
</form>
</body>
</html>