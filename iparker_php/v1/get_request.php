<?php
 require_once('dbconfig.php');
 
 $sql = "SELECT `id`, `username`, `vehicle_no`, `email`, `parkingname`, `date`, `timeFrom`, `timeTo`, `flag` FROM `parkingrequest`  ";
 
 $res = mysqli_query($con,$sql);
 
 $result = array();
 
 while($row = mysqli_fetch_array($res)){
 array_push($result,array('id'=>$row['id'],'username'=>$row['username'],'vehicle_no'=>$row['vehicle_no'], 'email'=>$row['email'], 'parkingname'=>$row['parkingname'],'date'=>$row['date'],'timeFrom'=>$row['timeFrom'],'timeTo'=>$row['timeTo']));
 }
 
 echo json_encode(array("result"=>$result));
 
 mysqli_close($con);