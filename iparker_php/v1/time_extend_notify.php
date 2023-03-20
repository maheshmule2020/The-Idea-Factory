
<?php
 require_once('dbconfig.php');

 $email = $_GET['email'];
 $sql = "SELECT * FROM `parkingrequest` WHERE email='$email' and flag='1' and timeTo < (NOW() - INTERVAL 1 MINUTE) ";

 if(mysqli_query($con,$sql)) 
 $res = mysqli_query($con,$sql);
 
 $result = array();
 
 while($row = mysqli_fetch_array($res)){
 array_push($result,array('id'=>$row['id'],'vehicle_no'=>$row['vehicle_no'], 'email'=>$row['email'], 'pname'=>$row['pname'],
 	'date'=>$row['date'],'flag'=>$row['flag']));
 }
 
 echo json_encode(array("result"=>$result));
 
 mysqli_close($con);