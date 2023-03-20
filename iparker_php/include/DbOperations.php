<?php 
 
    class DbOperations{
 
        private $con; 
 
        function __construct(){
 
            require_once dirname(__FILE__).'/DbConnect.php';
 
            $db = new DbConnect();
 
            $this->con = $db->connect();
 
        }
 
        /*CRUD -> C -> CREATE */

        public function createUser($name,$email,$password,$mobile,$area,$cardno,$cvv,$amount){
              
                $stmt = $this->con->prepare("INSERT INTO `user_reg`(`id`, `name`, `email`, `password`, `mobile`, `area`, `cardno`, `cvv`, `amount`) VALUES (NULL,?,?,?,?,?,?,?,?);");
                $stmt->bind_param("ssssssss",$name,$email,$password,$mobile,$area,$cardno,$cvv,$amount);
 
                if($stmt->execute()){
                    return 1; 
                }else{
                    return 2; 
                }
              
            }

           
           
 
        public function createAdmin($name,$mobile,$email,$password){
              
                $stmt = $this->con->prepare("INSERT INTO `admin` (`id`,`Name`,`mobile`, `email`, `Password`) VALUES (NULL,?,?,?,?);");
                $stmt->bind_param("ssss",$name,$mobile,$email,$password);
 
                if($stmt->execute()){
                    return 1; 
                }else{
                    return 2; 
                }
              
            }
			
		 public function upinfo($lat,$lon,$pname,$slots,$charges,$area){
              
                $stmt = $this->con->prepare("INSERT INTO `parking_area` (`loc_id`,`lat`, `lon`, `pname`, `avail_slots`, `rate`, `ptype`) VALUES (NULL,?,?,?,?,?,?);");
                $stmt->bind_param("ssssss",$lat,$lon,$pname,$slots,$charges,$area);
 
                if($stmt->execute()){
                    return 1; 
                }else{
                    return 2; 
                }
              
            }
			
			

    
            public function userLogin($email, $password){
        
            $stmt = $this->con->prepare("SELECT id FROM 
                 user_reg WHERE email = ? AND password = ?");
            $stmt->bind_param("ss",$email,$password);
            $stmt->execute();
            $stmt->store_result(); 
            return $stmt->num_rows > 0; 
        }

         public function getUserByUsername($email){
            $stmt = $this->con->prepare("SELECT * FROM 
                user_reg WHERE email = ?");
            $stmt->bind_param("s",$email);
            $stmt->execute();
            return $stmt->get_result()->fetch_assoc();
        }



       /*public function createAdmin($name,$mobile,$email,$password){
              
                $stmt = $this->con->prepare("INSERT INTO `admin_reg`(`id`, `name`, `mobile`, `email`, `password`) VALUES (NULL,?,?,?,?);");
                $stmt->bind_param("ssss",$name,$mobile,$email,$password);
 
                if($stmt->execute()){
                    return 3; 
                }else{
                    return 4; 
                }
              
            }*/
           
            
            public function adminLogin($email, $password){
			$flag="1";
            $stmt = $this->con->prepare("SELECT id FROM 
                 admin WHERE email = ? AND Password = ? AND flag='$flag'");
            $stmt->bind_param("ss",$email,$password);
            $stmt->execute();
            $stmt->store_result(); 
            return $stmt->num_rows > 0; 
        }

         public function getAdminByUsername($email){
            $stmt = $this->con->prepare("SELECT * FROM 
                admin WHERE email = ?");
            $stmt->bind_param("s",$email);
            $stmt->execute();
            return $stmt->get_result()->fetch_assoc();
        }

    

    }
        
 
    