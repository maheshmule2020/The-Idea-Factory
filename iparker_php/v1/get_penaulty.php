<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $email = $_POST['email'];
 $amount=$_POST['amount'];
 
  require_once('dbconfig.php');
  $sql = "SELECT * FROM `parkingrequest` WHERE email='$email' and flag='1' and timeTo < (NOW() - INTERVAL 2 MINUTE) ";


 if(mysqli_query($con,$sql)){
 
  $sql = "UPDATE `user_reg` SET `amount`= amount - '$amount' WHERE email='$email' ";
  if(mysqli_query($con,$sql)){
	echo "Amount Deducted";
  }else{
  	echo "Errorrr";
  }

 }
 
 mysqli_close($con);
 }else{
 echo "Error";
 }