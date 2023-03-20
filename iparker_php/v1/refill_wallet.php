<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $cardno = $_POST['cardno'];
 $cvv = $_POST['cvv'];
 $amount=$_POST['amount'];
 
  require_once('dbconfig.php');
 
 $sql = "UPDATE `user_reg` SET `amount`= amount + '$amount' WHERE cardno='$cardno'AND cvv='$cvv'";
 
 if(mysqli_query($con,$sql)){
 echo "wallete refilled";
 }
 
 mysqli_close($con);
 }else{
 echo "Error";
 }