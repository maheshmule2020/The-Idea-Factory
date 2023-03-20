<?php
 require_once('dbconfig.php');
 $id="0";
 $sql = "SELECT `id`, `Name`, `mobile`, `email`, `flag` FROM `admin` WHERE flag ='$id' ";
 
 $res = mysqli_query($con,$sql);
 
 $result = array();
 
 while($row = mysqli_fetch_array($res)){
 array_push($result,array('id'=>$row['id'],'name'=>$row['Name'],'mobile'=>$row['mobile'], 'email'=>$row['email'], 'flag'=>$row['flag']));
 }
 
 echo json_encode(array("result"=>$result));
 
 mysqli_close($con);