var api = {
  findAll: function () {
    $.ajax({
      type: "GET",
      url: "/member",
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    })
      .done(function (response) {
        console.log(JSON.stringify(response));
        $.each(response, function (index, el) {
          console.log(index + ":" + el);
        });
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
  findById: function () {
    var id = $("#findbyid-id").val();

    $.ajax({
      type: "GET",
      url: "/member/" + id,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    })
      .done(function (response) {
        console.log(JSON.stringify(response));
        $.each(response, function (index, el) {
          console.log(index + ":" + el);
        });
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
  findByPhone: function () {
    var phone = $("#findbyphone-phone").val();

    $.ajax({
      type: "GET",
      url: "/member/phone/" + phone,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    })
      .done(function (response) {
        console.log(JSON.stringify(response));
        $.each(response, function (index, el) {
          console.log(index + ":" + el);
        });
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
  save: function () {
    var data = {
      memberName: $("#save-name").val(),
      memberPhone: $("#save-phone").val(),
    };

    $.ajax({
      type: "POST",
      url: "/member",
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(data),
    })
      .done(function (response) {
        console.log(JSON.stringify(response));
        $.each(response, function (index, el) {
          console.log(index + ":" + el);
        });
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
  update: function (table, data) {
    console.log(data);
    $.ajax({
      type: "PUT",
      url: "/"+ table,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(data)
    })
      .done(function (response) {
        if (response == false){
            alert('수정 실패');
        }
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
  remove: function (table, id) {
    $.ajax({
      type: "DELETE",
      url: "/"+ table + "/" + id,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    })
      .done(function (response) {
        if (response == false){
            alert('삭제 실패');
        }
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};
