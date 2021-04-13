$(document).ready(
  function () {
      $("#form").validate({
          rules : {
              "confirm-password" : {
                  equalTo : "#password"
              }
          },
          messages: {
              "confirm-password": {
                  equalTo: "Password mismatch"
              }
          }
      });
  }
);
