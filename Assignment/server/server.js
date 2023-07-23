const fs = require('fs');
const path = require('path');
const exec = require('child_process').exec;
const mysql = require('mysql');

const folderPath = path.join(__dirname, 'MOB403');

// Kết nối đến cơ sở dữ liệu MySQL
const connection = mysql.createConnection({
  host: 'databases.000webhost.com',
  user: 'id16540630_tuvvph09410',
  password: 'TKLtJs2&fZ?lhRAW',
  database: 'id16540630_android_networking_mob403_vuvantu'
});

connection.connect(function (err) {
  if (err) {
    console.error('Error connecting: ' + err.stack);
    return;
  }
  console.log('Connected as id ' + connection.threadId);
});

// Đọc danh sách các file PHP trong thư mục MOB403
fs.readdir(folderPath, function (err, files) {
  if (err) {
    console.log(err);
    return;
  }
  files.forEach(function (file) {
    if (path.extname(file) === '.php') {
      const filePath = path.join(folderPath, file);
      // Thực thi từng file PHP
      exec("php " + filePath, function (error, stdout, stderr) {
        if (error) {
          console.log(error);
        } else {
          console.log(stdout);
        }
      });
    }
  });
});

// Truy vấn dữ liệu từ cơ sở dữ liệu
connection.query('SELECT * FROM your_table', function (error, results, fields) {
  if (error) throw error;
  // Xử lý kết quả truy vấn tại đây
  console.log(results);
});

connection.end();
