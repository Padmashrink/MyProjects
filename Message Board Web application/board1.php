
<?php
session_start();?>
<html>
<body>
<table>
<?php
if(!isset($_SESSION['username1']))
{
	header("location: board.php");
} 
else{
     $user1 = $_SESSION['username1'];
     try {
         $dbname = dirname($_SERVER["SCRIPT_FILENAME"]) . "/mydb.sqlite";
         $dbh = new PDO("sqlite:$dbname");
         $dbh->beginTransaction();
         $user1 = $_SESSION['username1'];
         $stmt = $dbh->prepare("select * from posts, users where username = postedby");
         $stmt->execute(); 
         print "<pre>";
         while ($row = $stmt->fetch()) {
             echo "<tr>";
			 echo "<td>". $row['id'] . "</td>";
			 echo "<td>". $row['postedby']. "</td>";
			 echo "<td>". $row['fullname']. "</td>";
			 echo "<td>". $row['follows']. "</td>";
			 echo "<td>". $row['datetime']. "</td>";
			 echo "<td>". $row['message']. "</td>";
			 echo '<td><form action ="message.php?id='.$row['id'].'" method ="POST"><button type="submit" name="reply" value="'.$row['id'].'"/>reply</form></td>';
			 echo "</tr>";
		}
         print "</pre>"; 
         $dbh->commit();
         if(isset($_POST['logout'])){
             unset($_SESSION['username1']);
	         session_destroy();
	         header("location: board.php");
	         return;
         }
         if(isset($_POST['newmessage'])){
             header("location: newmessage.php");
	         return;
         }
         if(isset($_POST['reply'])){
             header("location: message.php");
	         return;
         }
     }
     catch (PDOException $e) {
         print "Error!: " . $e->getMessage() . "<br/>";
         die();
     }    
}
?>
</table>
<form action ="board.php" method ="POST">
<input type="submit" name="logout" value="logout" />
</form>
<form action ="newmessage.php" method ="POST">
<input type="submit" name="newmessage" value="new message" />
</form>
</body>
</html>