<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $timeTo = $_POST['timeTo'];
 $email = $_POST['email'];
 
 require_once('dbconfig.php');

 $sql = "UPDATE `parkingrequest` SET `timeTo`='$timeTo' WHERE email='$email' ";
 
 if(mysqli_query($con,$sql)){
 echo "Time extended successfully";
 }
 
 mysqli_close($con);
 }else{
 echo "Error";
 }