<?php
$hostname_localhost ="*****";
$database_localhost ="*****";
$username_localhost ="*****";
$password_localhost ="*****";
$localhost = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
$username = $_POST['username'];
$password = $_POST['password'];
 
if(isset($_POST['username']) && ($_POST['password'])){
	$query_search = "select * from tbl_user where username = '".$username."' AND password = '".$password. "'";
	$query_exec = mysqli_query($localhost, $query_search); //or die(mysqli_error());
	$rows = mysqli_num_rows($query_exec);
	//echo $rows;
	 if($rows == 1) { 
 		echo "User Found"; 
	 }
 	else  {
    		echo "No Such User Found"; 
	}
}
?>