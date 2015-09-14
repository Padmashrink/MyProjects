

<html>
<head>
<title>ALBUMS</title>
<script type="text/javascript">
function image(displayimage)
{
	 document.getElementById("displayimage1").src = displayimage;
}
</script>
</head>
<div align="center">
<body>
<h2> ALBUMS </h2>
<form enctype="multipart/form-data" action="album.php" method="POST">    
<input type="hidden" name="MAX_FILE_SIZE" value="3000000" />    
Select Image to upload: <input name="filename" type="file" /><br/>    
<input type="submit" value="Submit" /> 
</form> 

<?php
error_reporting(E_ALL);
ini_set('display_errors','On');

set_time_limit(0);
require_once("DropboxClient.php");

$dropbox = new DropboxClient(array(
	'app_key' => "*****",      
	'app_secret' => "*****",   
	'app_full_access' => false,
),'en');

$access_token = load_token("access");
if(!empty($access_token)) {
	$dropbox->SetAccessToken($access_token);
}

elseif(!empty($_GET['auth_callback'])) 
{
	$request_token = load_token($_GET['oauth_token']);
	if(empty($request_token)) die('Request token not found!');
	
	$access_token = $dropbox->GetAccessToken($request_token);	
	store_token($access_token, "access");
	delete_token($_GET['oauth_token']);
}

if(!$dropbox->IsAuthorized())
{
	$return_url = "http://".$_SERVER['HTTP_HOST'].$_SERVER['SCRIPT_NAME']."?auth_callback=1";
	$auth_url = $dropbox->BuildAuthorizeUrl($return_url);
	$request_token = $dropbox->GetRequestToken();
	store_token($request_token, $request_token['t']);
	die("Authentication required. <a href='$auth_url'>Click here.</a>");
}

$files = $dropbox->GetFiles("",false);

//To upload the selected image. If the file is selected, then upload
if(isset($_FILES['filename']))
{
     $file = $_FILES['filename']['name'];               
	 $dropbox->uploadFile($_FILES['filename']['tmp_name'], $file);
}

$files = $dropbox->GetFiles("",false);

//To display file names in the Dropbox
echo "<h5>Images in the Dropbox:</h5>";

echo "<table border='1'><tr><td><ul>";

if(!empty($files) && !isset($_GET['delete'])) {
    $file = reset($files);
	foreach ($files as $files1)
	{     
	     $displayimage=$dropbox->GetLink($files1,false);
		 $fromjs = "image('".$displayimage."')";
		 echo '<form action="album.php" method="GET">';
	     echo '<li><a href="album.php" onClick="'.$fromjs.';return false">'.basename($files1->path).'</a>  <input type="hidden" name="delete" value='.basename($files1->path).'/><input type="submit" name="delet1" value="Delete"/></li>';
		 echo '</form>';
    }
}

//To delete image from the Dropbox
if(isset($_GET['delete']))
{
$dropbox->Delete($_GET['delete']);
$files = $dropbox->GetFiles("",false);
$file = reset($files);
foreach ($files as $files1)
	{     
	     $displayimage=$dropbox->GetLink($files1,false);
		 $fromjs = "image('".$displayimage."')";
		 echo '<form action="album.php" method="GET">';
	     echo '<li><a href="album.php" onClick="'.$fromjs.';return false">'.basename($files1->path).'</a>  <input type="hidden" name="delete" value='.basename($files1->path).'/><input type="submit" name="delet1" value="Delete"/></li>';
		 echo '</form>';
    }
}

function store_token($token, $name)
{
	if(!file_put_contents("tokens/$name.token", serialize($token)))
		die('<br />Could not store token! <b>Make sure that the directory `tokens` exists and is writable!</b>');
}

function load_token($name)
{
	if(!file_exists("tokens/$name.token")) return null;
	return @unserialize(@file_get_contents("tokens/$name.token"));
}

function delete_token($name)
{
	@unlink("tokens/$name.token");
}

function enable_implicit_flush()
{
	@apache_setenv('no-gzip', 1);
	@ini_set('zlib.output_compression', 0);
	@ini_set('implicit_flush', 1);
	for ($i = 0; $i < ob_get_level(); $i++) { ob_end_flush(); }
	ob_implicit_flush(1);
	echo "<!-- ".str_repeat(' ', 2000)." -->";
}

echo "</ul></td></tr></table>";

?>
<img id="displayimage1" style="width:150px;height:150px;">&nbsp;</img>
</body>
</div>
</html>
