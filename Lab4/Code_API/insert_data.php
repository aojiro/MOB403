<?php
$server='localhost';
 $password='TKLtJs2&fZ?lhRAW';
 $database='id16540630_android_networking_mob403_vuvantu';
 $connect=new mysqli($server,$user,$password,$database);
if($connect->connect_error)
{
    die("Connection Failed: " .$connect->connect_error);
}
if(isset($_POST['name']) && isset($_POST['email']) && isset($_POST['encrypted_password'])){
    $name=$_POST['name'];
    $email=$_POST['email'];
    $encrypted_password=$_POST['encrypted_password'];
    $sql="INSERT INTO account (name,email,encrypted_password) VALUES ('$name','$email','$encrypted_password')";
    if($connect->query($sql) === TRUE){
        $response["success"]=1;
        $response["message"]="Account successfully created.";
        echo json_decode($response);
    }else{
        $response["success"]=0;
        $response["message"]="Required field(s) is missing."; 
        echo json_decode($response);}
}
else{
    $response["success"]=0;
    $response["message"]="Required field(s) is missing."; 
    echo json_decode($response);}
    $connect->close();
    ?>