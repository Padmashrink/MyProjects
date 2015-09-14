<?php
require 'PHPMailer-master/PHPMailerAutoload.php';
require 'PHPMailer-master/class.phpmailer.php';
$response = array();

$hostname_localhost ="localhost";
$database_localhost ="mydatabase";
$username_localhost ="root";
$password_localhost ="";
$localhost = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost) or die(print_r("DB conn error"));

if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) 
{  
    $username = $_POST['name'];
	$email = $_POST['email'];
    $password = md5($_POST['password']);
    
    $result = mysqli_query($localhost, "insert into user_details(username,email,password) values('$username','$email','$password')");

    if ($result)
	{
        $mail = new PHPMailer();
$mail->IsSMTP();
$mail->SMTPAuth = true; // enable SMTP authentication
$mail->SMTPSecure = "ssl"; 
$mail->Host = "plus.smtp.mail.yahoo.com";
$mail->Port = 465; // set the SMTP port
$mail->Username = "anu1814@yahoo.com";
$mail->Password = "Anuabhi@39"; 
$mail->From = "anu1814@yahoo.com";
$mail->FromName = "macbuddy";
$mail->AddAddress("anuraagforever@gmail.com");
$mail->Subject = "Test PHPMailer Message"." ".$password;
$mail->Body = "Hi! \n\n This was sent with phpMailer_example3.php.";

if(!$mail->Send())
{
    echo 'Message was not sent.';
    echo 'Mailer error: ' . $mail->ErrorInfo;
}
else
{
    echo 'Message has been sent.';
}
        $response["success"] = 1;
        $response["message"] = "User Registered successfully";
        echo json_encode($response);
    }
	else
	{
      $response["success"] = 0;
      $response["message"] = "Error in registration! Please try again";
      echo json_encode($response);  
    }
 }
 
else 
 {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
 }
?>