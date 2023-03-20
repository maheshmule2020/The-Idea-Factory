
<?php 
 
require_once '../include/DbOperations.php';
 
$response = array(); 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['email']) and isset($_POST['password'])){
        $db = new DbOperations(); 
 
        if($db->userLogin($_POST['email'], $_POST['password'])){
            $user = $db->getUserByUsername($_POST['email']);
            $response['error'] = false; 
            $response['id'] = $user['id'];
             $response['name'] = $user['name'];
            $response['email'] = $user['email'];
             $response['area'] = $user['area'];
            
        }else{
            $response['error'] = true; 
            $response['message'] = "Invalid username or password";          
        }
 
    }else{
        $response['error'] = true; 
        $response['message'] = "Required fields are missing";
    }
}
 
echo json_encode($response);
?>