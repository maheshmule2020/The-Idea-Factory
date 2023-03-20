<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $email = $_POST['email'];
 $amount=$_POST['amount'];
 
require_once('dbconfig.php');
 
 $sql = "UPDATE `user_reg` SET `amount`= amount - '$amount' WHERE email='$email' ";
 
 if(mysqli_query($con,$sql)){
 echo "Amount Deducted";
 }
 
 mysqli_close($con);
 }else{
 echo "Error";
 }