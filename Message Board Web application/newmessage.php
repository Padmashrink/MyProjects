
<?php
session_start();
if(!isset($_SESSION['username1']))
{
	header("location: board.php");
} 
else{
     $user1 = $_SESSION['username1'];
     $message1 = $_POST['message'];
     $uniquenum = uniqid();
     try {
         $dbname = dirname($_SERVER["SCRIPT_FILENAME"]) . "/mydb.sqlite";
         $dbh = new PDO("sqlite:$dbname");
         $dbh->beginTransaction();
         if(isset($_POST['posts'])){
             $dbh->exec("insert into posts(id,postedby,datetime,message) values('$uniquenum','$user1',datetime('now'),'$message1')")
               or die(print_r($dbh->errorInfo(), true));
			 header("location: board1.php");
             $dbh->commit();
         }
     }
     catch (PDOException $e) {
         print "Error!: " . $e->getMessage() . "<br/>";
         die();
     } 
}
?>
<html>
<body>
<form action ="" method ="POST">
<textarea name="message" rows= "20" cols= "50"></textarea>
<input type="submit" name="posts" value="post" />
</form>
</body>
</html>