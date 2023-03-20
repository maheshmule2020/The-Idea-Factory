<?php
	$objConnect = mysqli_connect("localhost","root","","iparker");
	//$objDB = mysqli_select_db("");

	$strSQL = "SELECT * FROM `parking_area`  ORDER BY loc_id  ASC ";

	$objQuery = mysqli_query($objConnect,$strSQL) or die(mysqli_error());
	$arrRows = array();
	$arryItem = array();
	while($arr = mysqli_fetch_array($objQuery)) {
		$arryItem["loc_id"] = $arr["loc_id"];
		$arryItem["lat"] = $arr["lat"];
		$arryItem["lon"] = $arr["lon"];
		$arryItem["pname"] = $arr["pname"];
		$arryItem["avail_slots"] = $arr["avail_slots"];
		$arryItem["rate"] = $arr["rate"];
		$arryItem["ptype"] = $arr["ptype"];
		
		$arrRows[] = $arryItem;
	}
		
echo json_encode($arrRows);
?>