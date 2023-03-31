var api = {
  findAll: function (table) {
    var object = null;

    $.ajax({
      type: "GET",
      async: false,
      url: "/" + table,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    }).done(function (response) {
      object = response;
    });

    return object;
  },
  search: function (table, type, keyword) {
    var object = null;

    $.ajax({
      type: "GET",
      async: false,
      url: "/" + table + "/" + type + "/" + keyword,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    }).done(function (response) {
      object = response;
    });

    return object;
  },
  find: function (table, columnName, data) {
    var object = null;

    $.ajax({
      type: "GET",
      async: false,
      url: "/" + table + "/" + columnName + "/" + data,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    }).done(function (response) {
      object = response;
    });

    return object;
  },
  save: function (table, data) {
    var success = false;

    $.ajax({
      type: "POST",
      async: false,
      url: "/" + table,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(data),
    })
      .done(function (response) {
        if (response == false) {
          success = false;
        } else {
          success = true;
        }
      })
      .fail(function (error) {
        success = false;
      });

    return success;
  },
  updateAll: function (table, data) { // 리소스 전부를 업데이트 할때
    var success = false;
    $.ajax({
      type: "PUT",
      async: false,
      url: "/" + table,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(data),
    })
      .done(function (response) {
        if (response == false) {
          success = false;
        } else {
          success = true;
        }
      })
      .fail(function (error) {
        success = false;
      });

    return success;
  },
    update: function (table, resourceIdx, data) { // 리소스 일부를 업데이트 할때
      var success = false;
      $.ajax({
        type: "PATCH",
        async: false,
        url: "/" + table + "/" + resourceIdx,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
      })
        .done(function (response) {
          if (response == false) {
            success = false;
          } else {
            success = true;
          }
        })
        .fail(function (error) {
          success = false;
        });

      return success;
    },
  remove: function (table, id) {
    var success = false;
    $.ajax({
      type: "DELETE",
      async: false,
      url: "/" + table + "/" + id,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    })
      .done(function (response) {
        if (response == false) {
          success = false;
        } else {
          success = true;
        }
      })
      .fail(function (error) {
        success = false;
      });

    return success;
  },
    removeOne: function (table, resourceIdx, data) {
      var success = false;
      $.ajax({
        type: "DELETE",
        async: false,
        url: "/" + table + "/" + resourceIdx,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
      })
        .done(function (response) {
          if (response == false) {
            success = false;
          } else {
            success = true;
          }
        })
        .fail(function (error) {
          success = false;
        });

      return success;
    }
};
