var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
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
	search: function (table, keyword, location, page) {
		var object = null;

		$.ajax({
			type: "GET",
			async: false,
			url: "/" + table + "/keyword/" + keyword + "/" + location + "/" + page,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
		}).done(function (response) {
			object = response;
		});

		return object;
	},
	searchUsingFilter: function (table, filter, content, keyword, page) {
		var object = null;
		$.ajax({
			type: "GET",
			async: false,
			url:
				"/" +
				table +
				"/" +
				filter +
				"/" +
				content +
				"/keyword/" +
				keyword +
				"/" +
				page,
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
		var success = 0;

		$.ajax({
			type: "POST",
			async: false,
			url: "/" + table,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
		})
			.done(function (response) {
				success = response;
				console.log(response);
			})
			.fail(function (error) {
				success = 0;
			});

		return success;
	},

	updateAll: function (table, data) {
		// 리소스 전부를 업데이트 할때
		var success = 0;
		$.ajax({
			type: "PUT",
			async: false,
			url: "/" + table,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			beforeSend: function (xhr) {
				xhr.setRequestHeader(header, token);
			},
			data: JSON.stringify(data),
		})
			.done(function (response) {
				success = response;
			})
			.fail(function (error) {
				success = 0;
			});

		return success;
	},
	update: function (table, resourceIdx, data, plusUrl = "") {
		// 리소스 일부를 업데이트 할때

		var success = false;
		if (plusUrl != "") {
			plusUrl = plusUrl + "/";
		}
		$.ajax({
			type: "PATCH",
			async: false,
			url: "/" + table + "/" + plusUrl + resourceIdx,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			beforeSend: function (xhr) {
				xhr.setRequestHeader(header, token);
			},
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
		var success = 0;
		$.ajax({
			type: "DELETE",
			async: false,
			url: "/" + table + "/" + id,
			dataType: "json",
			beforeSend: function (xhr) {
				xhr.setRequestHeader(header, token);
			},
			contentType: "application/json; charset=utf-8",
		})
			.done(function (response) {
				success = response;
			})
			.fail(function (error) {
				success = 0;
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
			beforeSend: function (xhr) {
				xhr.setRequestHeader(header, token);
			},
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
};
