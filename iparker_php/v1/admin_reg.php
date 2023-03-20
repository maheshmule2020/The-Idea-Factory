
<?php 
 
require_once '../include/DbOperations.php';
 
$response = array(); 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(       
              isset($_POST['Name']) and
              isset($_POST['mobile'])and
              isset($_POST['email']) and
              isset($_POST['Password']) and
			   isset($_POST['area']) and
              isset($_POST['slots'])and
              isset($_POST['charges']) and
              isset($_POST['pname']) and
			  isset($_POST['lat'])and
			  isset($_POST['lon']))
        {
        //operate the data further 
 
      $db = new DbOperations();

      $result = $db->createAdmin($_POST['Name'],$_POST['mobile'], $_POST['email'],
        $_POST['Password']
                  );

		 $result1 = $db->upinfo($_POST['lat'],$_POST['lon'], $_POST['pname'],
        $_POST['slots'],$_POST['charges'],$_POST['area']
                  );
      if($result== 1 and $result1== 1){

         $response['error'] = false; 
        $response['message'] = "Admin registered successfully";

      }elseif($result==2 and $result1== 2){

         $response['error'] = true; 
    $response['message'] = "some error occured please try again";
      }
    
    }else{
        $response['error'] = true; 
        $response['message'] = "Required fields are missing";
    }
}else{
    $response['error'] = true; 
    $response['message'] = "Invalid Request";
}

echo json_encode($response);
