
<?php 
 
require_once '../include/DbOperations.php';
 
$response = array(); 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(       
              isset($_POST['name']) and
              isset($_POST['email']) and
              isset($_POST['password']) and
              isset($_POST['mobile']) and
                isset($_POST['area']) and  
                 isset($_POST['cardno']) and
              isset($_POST['cvv']) and
                isset($_POST['amount']))
        {
        //operate the data further 
 
      $db = new DbOperations();

      $result = $db->createUser($_POST['name'], $_POST['email'],
                  $_POST['password'],$_POST['mobile'],$_POST['area'],$_POST['cardno'],$_POST['cvv'],$_POST['amount']
                  );

      if($result== 1){

         $response['error'] = false; 
        $response['message'] = "User registered successfully";

      }elseif($result==2){

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
