
<?php
include 'dbconfig.php';

 $lat = $_POST['lat'];
 $lon = $_POST['lon'];
 $pname = $_POST['pname'];
 $avail_slots = $_POST['avail_slots'];
 $rate = $_POST['rate'];
 $ptype = $_POST['ptype'];
 
 $Sql_Query = "INSERT INTO `parking_area`(`lat`, `lon`, `pname`, `avail_slots`, `rate`, `ptype`) VALUES  ('$lat','$lon','$pname','$avail_slots','$rate','$ptype')";
 
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'location sent Successfully';
 
 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>