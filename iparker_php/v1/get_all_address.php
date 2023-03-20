<?php
 require_once('dbconfig.php');
 

 $sql = "SELECT `LocationID`, `Latitude`, `Longitude`, `address` FROM `get_location`;";
 
 $res = mysqli_query($con,$sql);
 
 $result = array();
 
 while($row = mysqli_fetch_array($res)){
 array_push($result,array('LocationID'=>$row['LocationID'],'Latitude'=>$row['Latitude'],'Longitude'=>$row['Longitude'],'address'=>$row['address']));
 }
 
 echo json_encode(array("result"=>$result));
 
 mysqli_close($con);