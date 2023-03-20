<?php
 require_once('dbconfig.php');
 
 
 $email = $_GET['email'];
 $sql = "SELECT  `amount` FROM `user_reg` WHERE email='$email'";
 
 $res = mysqli_query($con,$sql);
 
 $result = array();
 
 while($row = mysqli_fetch_array($res)){
 array_push($result,array('amount'=>$row['amount']));
 }
 
 echo json_encode(array("result"=>$result));
 
 mysqli_close($con);