
<?php
include 'dbconfig.php';

 $username = $_POST['username'];
 $vehicle_no = $_POST['vehicle_no'];
 $email = $_POST['email'];
 $parkingname = $_POST['parkingname'];
 $date = $_POST['date'];
 $timeFrom = $_POST['timeFrom'];
 $timeTo = $_POST['timeTo'];

 
 $Sql_Query = "INSERT INTO `parkingrequest`(`username`, `vehicle_no`, `email`, `parkingname`, `date`, `timeFrom`, `timeTo`) VALUES ('$username','$vehicle_no','$email','$parkingname','$date','$timeFrom','$timeTo')";
 
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'request sent Successfully';
 
 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>