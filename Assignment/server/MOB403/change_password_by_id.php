<?php
 $server='localhost';
 $user='id16540630_tuvvph09410';
 $password='TKLtJs2&fZ?lhRAW';
 $database='id16540630_android_networking_mob403_vuvantu';
 $connect=new mysqli($server,$user,$password,$database);

if($connect->connect_error)
{
    die("Connection Failed: " .$connect->connect_error);
}
if(isset($_POST['id']) && isset($_POST['new_password'])){
    $id=$_POST['id'];
    $new_password=$_POST['new_password'];
    $update="UPDATE account SET encrypted_password= '.$new_password' WHERE id='.$id'";
   if($connect->query($update) === TRUE){
    $response["success"]=1;
    $response["message"]="Update password successfully.";
    echo json_decode($response);
   }else{
    $response["success"]=0;
    $response["message"]="Required field(s) is missing."; 
    echo json_decode($response);}
    

}
else{  
    $response["success"]=0;
    $response["message"]="Required field(s) is missing."; 
    echo json_encode($response);
}
$connect->close();
?>