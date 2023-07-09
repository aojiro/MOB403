<?php
if (isset($_POST['chieudai']) && isset($_POST['chieurong'])) {
    $dai = $_POST['chieudai'];
    $rong = $_POST['chieurong'];
    $cv = 2 * ($dai + $rong);
    $dt = $dai * $rong;
    echo "Chu vi: ".$cv . " | Dien tich: " .$dt;
} else {
    echo "Missing POST parameters chieudai and chieurong";
}
?>
