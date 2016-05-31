var form;

function makeEditable() {
    form = $('#detailsForm');
}

function add() {
    form.find(':input').val('');
    $('#id').val(0);
    $('#editModal').modal();
}

function updateRow(id) {
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
            form.find("textArea[name='" + key + "']").val(value);
        });
        $('#editModal').modal();
    });
}

function removeRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNote('Deleted')
        }
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

var failedNote;

function closeNote() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNote(text) {
    closeNote();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNote(event, jqXHR, options, jsExc) {
    closeNote();
    var errorInfo = $.parseJSON(jqXHR.responseText);
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + '<br>' + errorInfo.cause + '<br>' + errorInfo.detail,
        type: 'error',
        layout: 'bottomRight'
    });
}

function renderEditButton(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-primary" onclick="updateRow(' + '\'' + row.id + '\'' + ');">Edit <span class="glyphicon glyphicon-edit"></span></a>';
    }
    return data;
}

function renderDeleteButton(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-danger" onclick="removeRow(' + '\'' + row.id + '\'' + ');">Delete <span class="glyphicon glyphicon-trash"></span></a>';
    }
    return data;
}