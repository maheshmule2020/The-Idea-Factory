<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){

 require_once('dbconfig.php'); 	
 
 $flag = $_POST['flag'];
 $id=$_GET['id'];
 
 
 $sql = "UPDATE `parkingrequest` SET `flag`='$flag' WHERE id ='$id'";
 
 if(mysqli_query($con,$sql)){
 echo "Accepted";
 }
 
 mysqli_close($con);
 }else{
 echo "Error";
 }