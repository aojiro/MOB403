<?php
 $server='localhost';
 $user='id16540630_tuvvph09410';
 $password='TKLtJs2&fZ?lhRAW';
 $database='id16540630_android_networking_mob403_vuvantu';
 $connect=new mysqli($server,$user,$password,$database);
 $response=array();
if($connect->connect_error)
{
    die("Connection Failed: " .$connect->connect_error);
}
if(isset($_POST['email']) && isset($_POST['encrypted_password'])){
    $email=$_POST['email'];
    $encrypted_password=$_POST['encrypted_password'];
    $result=$connect->query("SELECT * FROM account WHERE email='$email' AND encrypted_password = '$encrypted_password'");
    if($result->num_rows > 0){
             $response["account"] = array();
            while($row =$result->fetch_assoc()){
                $account=array();
                $account["id"]=$row["id"];
                $account["name"]=$row["name"];
                $account["email"]=$row["email"];
                $account["encrypted_password"]=$row["encrypted_password"];
                array_push($response["account"],$account);
                }
                $response["success"]=1;
                $response["message"] = "Đăng nhập thành công";
                echo json_encode($response);
    }
    else{
        $response["success"] = 0;
        $response["message"] = "Không đúng mật khẩu hoặc tài khoảni";
        echo json_encode($response);
    }

}
else{  
    $response["success"]=0;
    $response["message"]="Required field(s) is missing."; 
    echo json_encode($response);
}
$connect->close();
?>