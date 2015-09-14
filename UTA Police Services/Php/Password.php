<?php

$response = array();

$hostname_localhost ="*****";
$database_localhost ="*****";
$username_localhost ="*****";
$password_localhost ="*****";
$localhost = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost) or die(print_r("DB conn error"));

$emailid = $_POST['emailid'];
$password = $_POST['newPassword'];

$result = mysqli_query($localhost, "Update tbl_user set password = '".$password."' where emailid = '".$emailid."'");

if ($result)
{
    $response["success"] = 1;
    $response["message"] = "Success.";
    echo json_encode($response);
}
else
{
    $response["success"] = 0;
    $response["message"] = "An error occurred.";
    echo json_encode($response);  
} 
?>