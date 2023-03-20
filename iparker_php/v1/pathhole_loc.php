<?php
include 'dbconfig.php';

 $lat = $_POST['lat'];
 $lon = $_POST['lon'];
 $address = $_POST['address'];
 $note = $_POST['note'];
 $user_name = $_POST['user_name'];
 

 $Sql_Query = "INSERT INTO `user_location`(`lat`, `lon`, `address`, `note`, `user_name`) VALUES  ('$lat','$lon','$address','$note','$user_name')";
 
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'location sent Successfully';
 
 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>