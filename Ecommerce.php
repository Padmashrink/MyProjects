
<?php
session_start();
?>
<html>
<head><title>Buy Products</title></head>
<body>
<p>Shopping Basket:</p>
<!-- Putting items into the shopping basket -->
<table border="1">
<?php 
if(isset($_GET['buy'])){
foreach($_SESSION['shopping_basket'] as $item){
	 if($item['id'] == $_GET['buy']){
		 $_SESSION['basket'][] = array('id1'=>(string)$item['id'],'image1'=>(string)$item['image'],'name1'=>(string)$item['name'],'price1'=>(string)$item['price'],'offer1'=>(string)$item['offer']);
	}
}
$_SESSION['basket'] = array_map("unserialize", array_unique(array_map("serialize", $_SESSION['basket'])));
if(!(isset($_GET['delete'])) and (!(isset($_GET['clear'])))){		   
	 foreach($_SESSION['basket'] as $imp){?>
	     <tr><td><a href= <?php echo $imp['offer1'];?>><img src=<?php  echo $imp['image1']; ?>/></a></td><td><?php echo $imp['name1']; ?> </td><td><?php echo $imp['price1']; ?></td><td><a href=http://omega.uta.edu/~pvn4560/project3/buy.php?delete=<?php echo $imp['id1'];?>><?php echo "delete"; ?></a></td></tr>
		 <?php 
		 $totalPrice1+=$imp['price1'];
	}
}}
?>
</table>
<p/>
<!-- deleting the items from the shopping basket -->
<table border ="1">
<?php
if(isset($_SESSION['basket']))
{
   $countItems = count($_SESSION['basket']);
   if(isset($_GET['delete']))
   {
       for($row=0;$row<$countItems;$row++)
       {
	   if($_GET['delete'] == $_SESSION['basket'][$row]['id1'])
       {
            unset($_SESSION['basket'][$row]);
            $_SESSION['basket'] = array_values($_SESSION['basket']);
       }
     }
	 foreach($_SESSION['basket'] as $imp1){?>
	     <tr><td><a href= <?php echo $imp1['offer1'];?>><img src=<?php  echo $imp1['image1']; ?>/></a></td><td><?php echo $imp1['name1']; ?> </td><td><?php echo $imp1['price1']; ?></td><td><a href=http://omega.uta.edu/~pvn4560/project3/buy.php?delete=<?php echo $imp1['id1'];?>><?php echo "delete"; ?></a></td></tr>
        <?php $totalPrice1+=$imp1['price1'];
		  }}}?>
</table>
<?php if(isset($totalPrice1))
{?>
Total: $<?php echo $totalPrice1; }?>
<p/>
<?php
error_reporting(E_ALL);
ini_set('display_errors','On');
$xmlstr = file_get_contents('http://sandbox.api.ebaycommercenetwork.com/publisher/3.0/rest/CategoryTree?apiKey=*****&visitorUserAgent&visitorIPAddress&trackingId=7000610&categoryId=72&showAllDescendants=true');
$xml = new SimpleXMLElement($xmlstr);
?>
<p/>
<form action="buy.php" method="GET">
<input type="hidden" name="clear" value="1"/>
<input type="submit" value="Empty Basket"/>
</form>
<!-- clearing sessions -->
<?php if(isset($_GET['clear']))
{
     unset($_SESSION['shopping_basket']);
	 unset($_SESSION['basket']);
}?>
<p/>
<form action="buy.php" method="GET"> 
<fieldset><legend>Find products:</legend>
<label>Category: <select name="category"><option value="72">
<?php echo $xml->category->name; ?></option>
<optgroup label ="<?php echo $xml->category->name; ?>">
<?php foreach($xml->category->categories->category as $category_child) {?>
<option value = "<?php echo $category_child->attributes()->id; ?>">
<?php echo $category_child->name;?></option>
<optgroup label = "<?php echo $category_child->name;?>">
<?php foreach($category_child->categories->category as $category_child1){?> 
<option value="<?php echo $category_child1->attributes()->id; ?>">
<?php echo $category_child1->name; ?>
</option>
<?php }?> 
</optgroup>
<?php }?>
</optgroup>
</select></label>
<label>Search keywords: <input type="text" name="name"/><label>
<input type="submit" value="Search" />
</fieldset>
</form>
<?php 
if(isset($_GET['category'])){
   $categoryselected = $_GET['category'];
   
if(isset($_GET['name'])&&$_GET['name']!=null){
   $search = $_GET['name'];   
  
   $url = "http://sandbox.api.ebaycommercenetwork.com/publisher/3.0/rest/GeneralSearch?apiKey=*****TrackingId=7000610&visitorUserAgent&visitorIPAddress&numItems=20&categoryId=$categoryselected&keyword=$search";
   $utfurl = utf8_encode($url);
   $xmlstr1 = file_get_contents($utfurl);
   $xml1 = new SimpleXMLElement($xmlstr1); 
?>
<!-- displaying the search result -->
<table border="1">
<?php 
unset($_SESSION['shopping_basket']);
foreach($xml1->categories->category->items->product as $category_child2){
$resultid = $category_child2->attributes()->id;
$resultname = $category_child2->name;
$resultminprice = $category_child2->minPrice;
$resultdescription = $category_child2->fullDescription;
$resultimage = $category_child2->images[0]->image[0]->sourceURL;
$resultoffer = $category_child2->productOffersURL;
?>
<tr><td><a href=http://omega.uta.edu/~pvn4560/project3/buy.php?buy=<?php echo $resultid; ?>>
<img src=<?php echo $resultimage; ?>/></a></td>
<td><?php echo $resultname; ?></td>
<td><?php echo $resultminprice; ?></td>
<td><?php echo $resultdescription; ?></td></tr>
<?php echo "<pre>";
$_SESSION['shopping_basket'][]= array('id'=>(string)$resultid,'image'=>(string)$resultimage,'name'=>(string)$resultname,'price'=>(string)$resultminprice,'offer'=>(string)$resultoffer);
}
}}?>
</table>
<p/>
</body>
</html>

<!-- References : http://www.w3schools.com/php/   http://php.net/manual/en/features.sessions.php -->