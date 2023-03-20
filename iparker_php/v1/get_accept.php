<?php
 require_once('dbconfig.php');

 $email = $_GET['email'];
 $sql = "SELECT  `vehicle_no`, `email`, `pname`, `date`, `timeFrom` FROM `parkingrequest` WHERE flag=1 and email='$email'";
 
 $res = mysqli_query($con,$sql);
 
 $result = array();
 
 while($row = mysqli_fetch_array($res)){
 array_push($result,array('vehicle_no'=>$row['vehicle_no'], 'email'=>$row['email'], 'pname'=>$row['pname'],
 	'date'=>$row['date'],'timFrom'=>$row['timeFrom']));
 }
 
 echo json_encode(array("result"=>$result));
 
 mysqli_close($con);