<?php
require 'PHPMailer-master/PHPMailerAutoload.php';
require 'PHPMailer-master/class.phpmailer.php';
$response = array();

$hostname_localhost ="*****";
$database_localhost ="*****";
$username_localhost ="*****";
$password_localhost ="*****";
$localhost = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost) or die(print_r("DB conn error"));

if (isset($_POST['utaid']) && isset($_POST['username']) && isset($_POST['emailid']) && isset($_POST['emename']) && isset($_POST['emenum'])) 
{  
    $utaid = $_POST['utaid'];
    $username = $_POST['username'];
    $emailid = $_POST['emailid'];
    $emename = $_POST['emename'];
    $emenum = $_POST['emenum'];
    $verifyCode = md5($_POST['verifyCode']);
    
    
    $result = mysqli_query($localhost, "Insert into tbl_user(utaid,username,emailid,emename,emenum,verifyCode) values('$utaid','$username','$emailid','$emename','$emenum','$verifyCode')");
$messageBody = "Dear Customer,"."<br/>"."Thanks for signing up to UTA POLICE SERVICE"."<br/>"."This email is to verify you are '".$username."'."."<br/><br/>"."Please enter the below one time password on the application to register:"."<br/><br/>".

$_POST['verifyCode']

."<br/>"."If you're not '".$username."', you can ignore this email.";

        $mail = new PHPMailer();
        $mail->IsSMTP();
        $mail->SMTPAuth = true; // enable SMTP authentication
        $mail->SMTPSecure = "ssl"; 
        $mail->Host = "plus.smtp.mail.yahoo.com";
        $mail->Port = 465; // set the SMTP port
        $mail->Username = "******@yahoo.com";
        $mail->Password = "seproject"; 
        $mail->From = "******@yahoo.com";
        $mail->FromName = "UTA POLICE SERVICE One Time Password";
        $mail->AddAddress($emailid);
        $mail->Subject = "[UTA POLICE SERVICE] One Time Password '"." ".$emailid."'";
        $mail->Body = 'Dear Customer,
        
        Thanks for signing up to UTA POLICE SERVICE. This email is to verify you are '.$username.'
        
        Please enter the below one time password on the application to register:

'.$_POST['verifyCode'].'


If you are not '.$username.', you can ignore this email.';
$mail->Send();

    if ($result)
    {
        $response["success"] = 1;
        $response["message"] = "Successfuly Registered.";
        echo json_encode($response);
    }
    else
    {
       $response["success"] = 0;
       $response["message"] = "Oops! An error occurred.";
       echo json_encode($response);  
    }
 }
?>