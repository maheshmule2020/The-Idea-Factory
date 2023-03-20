<?php
	require 'dbconfig.php';
	
	if($_SERVER['REQUEST_METHOD']=='GET'){
		$code = $_GET['code'];
		$findstud = "select * from parkingrequest where qrinfo='$code'";
		$res = mysqli_query($con,$findstud);
		if(mysqli_num_rows($res)>0){
			$data=mysqli_fetch_array($res);
			$response['username']=$data['username'];
			$response['vehicle_no']=$data['vehicle_no'];
			$response['email']=$data['email'];
			$response['parkingname']=$data['parkingname'];
			$response['date']=$data['date'];
			$response['timeFrom']=$data['timeFrom'];
			$response['timeTo']=$data['timeTo'];

			//$response['bloodgrp']=$data['bloodgrp'];
			//$response['alltofood']=$data['alltofood'];
			//$response['alltomedicine']=$data['alltomedicine'];
			//$response['disease']=$data['disease'];
			$response['id']=$data['id'];
			$response['success']="200";
			$response['message']="Valid QRCode";
		}
		else
		{
			$response['success'] = "201";
			$response['message'] = "Invalid QRCode";
		}
		die(print_r(json_encode($response),true));
	}
?>