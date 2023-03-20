
<?php 
 
require_once '../include/DbOperations.php';
 
$response = array(); 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['email']) and isset($_POST['Password'])){
        $db = new DbOperations(); 
 
        if($db->adminLogin($_POST['email'], $_POST['Password'])){
            $user = $db->getAdminByUsername($_POST['email']);
            $response['error'] = false; 
            $response['id'] = $user['id'];
             $response['Name'] = $user['Name'];
            $response['email'] = $user['email'];
            
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