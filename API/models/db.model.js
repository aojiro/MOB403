const mongoose = require("mongoose");
const nameDB = "MOB403";
mongoose
  .connect(
    `mongodb+srv://Admin:admin@thangit.ky8asol.mongodb.net/${nameDB}?retryWrites=true&w=majority/`

  )
  .then(() => console.log("✅ Đã kết nối thành công đến mongodb."))
  .catch((error) =>
    console.error(`❌ Connect database is failed with error which is ${error}`)
  );
module.exports = { mongoose };
