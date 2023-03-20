
<?php
 require_once('dbconfig.php');

 $ID = $_GET['id'];
 $sql = "DELETE FROM `parkingrequest` WHERE id = '$ID';";
 

 if(mysqli_query($con,$sql))
{
 echo 'deleted';
}
else
{
 echo 'Something went wrong';
 }
 
 mysqli_close($con);
?>