$(document).ready(
  function () {
      $("#registration-form").validate({
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
