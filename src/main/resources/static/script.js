const usersFormEdit = $('#editModal');
const usersFormDelete = $('#deleteModal');
const usersTable = $('#usersTable');
const userFormAdd = $('#userAddForm');

userFormAdd.find(':submit').click(() => {
    addUser();
});

$('#nav-home-tab').click(() => {
    loadUsersTable();
});

$('#nav-profile-tab').click(() => {
    loadAddForm();
});

function loadUsersTable() {
    $('#nav-home-tab').addClass('active');
    $('#nav-home').addClass('show').addClass('active');
    $('#nav-profile-tab').removeClass('active');
    $('#nav-profile').removeClass('show').removeClass('active');
    showAllUsers();
}

function loadAddForm() {
    $('#nav-profile-tab').addClass('active');
    $('#nav-profile').addClass('show').addClass('active');
    $('#nav-home-tab').removeClass('active');
    $('#nav-home').removeClass('show').removeClass('active');
    addUserForm();
}

function showAllUsers() {
    fetch("/api/users").then(
        res => {
            res.json().then(
                data => {
                    usersTable.empty();
                    console.log(data);
                    if (data.length > 0) {
                        data.forEach((u) => {
                            _addUserRow(u);
                        })
                    }
                }
            );
        }
    );
}

function _addUserRow(user) {
    usersTable
        .append($('<tr>').attr('id', 'userRowId[' + user.id + ']')
            .append($('<td>').attr('id', 'userData[' + user.id + '][id]').text(user.id))
            .append($('<td>').attr('id', 'userData[' + user.id + '][userName]').text(user.userName))
            .append($('<td>').attr('id', 'userData[' + user.id + '][userLastName]').text(user.userLastName))
            .append($('<td>').attr('id', 'userData[' + user.id + '][age]').text(user.age))
            .append($('<td>').attr('id', 'userData[' + user.id + '][email]').text(user.email))
            .append($('<td>').attr('id', 'userData[' + user.id + '][roles]').text(user.roles.map(role => role.roleName)))
            .append($('<td>').append($('<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editModal">')
                .click(() => {
                    loadUserAndShowModalEditForm(user.id);
                }).text('Edit')))
            .append($('<td>').append($('<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">')
                .click(() => {
                    loadUserAndShowModalDeleteForm(user.id);
                }).text('Delete')))
        );
}

function loadUserAndShowModalEditForm(id) {
    fetch('/api/users/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (user) {
                usersFormEdit.find('#userIdEdit').val(id);
                usersFormEdit.find('#userNameEdit').val(user.userName);
                usersFormEdit.find('#userLastNameEdit').val(user.userLastName);
                usersFormEdit.find('#passwordEdit').val(user.password);
                usersFormEdit.find('#ageEdit').val(user.age);
                usersFormEdit.find('#emailEdit').val(user.email);
                usersFormEdit.find('.submit')
                    .text('Edit')
                    .removeClass('btn-danger')
                    .addClass('btn-primary')
                    .removeAttr('onClick')
                    .attr('onClick', 'updateUser(' + id + ');');
                usersFormEdit.find('#rolesEdit').empty();

                fetch('/api/roles').then(function (response) {
                    if (response.ok) {
                        usersFormEdit.find('#rolesEdit').empty();
                        response.json().then(roleList => {
                            roleList.forEach(role => {
                                usersFormEdit.find('#rolesEdit')
                                    .append($('<option>')
                                        .prop('selected', user.roles.filter(e => e.id === role.id).length)
                                        .val(role.id).text(role.roleName));
                            });
                        });
                    }
                })
                usersFormEdit.modal();
            });
        })
}

function loadUserAndShowModalDeleteForm(id) {
    fetch('/api/users/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (user) {
                console.log(user);
                usersFormDelete.find('#userIdDelete').val(id);
                usersFormDelete.find('#userNameDelete').val(user.userName);
                usersFormDelete.find('#lastNameDelete').val(user.userLastName);
                usersFormDelete.find('#passwordDelete').val(user.password);
                usersFormDelete.find('#ageDelete').val(user.age);
                usersFormDelete.find('#emailDelete').val(user.email);
                usersFormDelete.find('.submit')
                    .text('Delete')
                    .removeClass('btn-primary')
                    .addClass('btn-danger')
                    .removeAttr('onClick')
                    .attr('onClick', 'deleteUser(' + id + ');');
                usersFormDelete.find('#roles').empty();
                user.roles.forEach(rol => {
                    usersFormDelete.find('#roles')
                        .append($('<option>').val(rol.id).text(rol.roleName));
                })
                usersFormDelete.modal();
            });
        })
}

function updateUser(id) {
    let txt;
    if (($('#rolesEdit option:selected').length) === 2) {
        txt = [{
            "id": parseFloat($('#rolesEdit option[value=1]').val()),
            "roleName": $('#rolesEdit option[value=1]').text()
        },
            {"id": parseFloat($('#rolesEdit option[value=2]').val()), "roleName": $('#rolesEdit option[value=2]').text()}]
    } else if (($('#rolesEdit option:selected').text()) === "ROLE_ADMIN") {
        txt = [{
            "id": parseFloat($('#rolesEdit option[value=1]').val()),
            "roleName": $('#rolesEdit option[value=1]').text()
        },
            {"id": parseFloat($('#rolesEdit option[value=2]').val()), "roleName": $('#rolesEdit option[value=2]').text()}]
    } else if (($('#rolesEdit option:selected').text()) === "ROLE_USER") {
        txt = [{
            "id": parseFloat($('#rolesEdit option[value=1]').val()),
            "roleName": $('#rolesEdit option[value=1]').text()
        }]
    }
    let user = {
        'id': parseInt(usersFormEdit.find('#userIdEdit').val()),
        'userName': usersFormEdit.find('#userNameEdit').val(),
        'userLastName': usersFormEdit.find('#userLastNameEdit').val(),
        'password': usersFormEdit.find('#passwordEdit').val(),
        'age': usersFormEdit.find('#ageEdit').val(),
        'email': usersFormEdit.find('#emailEdit').val(),
        'roles': txt
    };
    let request = new Request('/api/users/', {
        method: 'PUT',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(user)
    });
    console.log("JSONED: " + JSON.stringify(user));
    fetch(request)
        .then(function (response) {
            response.json().then(function (userData) {
                console.log(userData);
                $('#userData\\[' + userData.id + '\\]\\[userName\\]').text(userData.userName)
                $('#userData\\[' + userData.id + '\\]\\[userLastName\\]').text(userData.userLastName)
                $('#userData\\[' + userData.id + '\\]\\[password\\]').text(userData.password)
                $('#userData\\[' + userData.id + '\\]\\[age\\]').text(userData.age)
                $('#userData\\[' + userData.id + '\\]\\[email\\]').text(userData.email)
                $('#userData\\[' + userData.id + '\\]\\[roles\\]').text(userData.roles.map(role => role.roleName));
                usersFormEdit.modal('hide');
                console.info("User with id = " + id + " was updated");
            });
        })
}

function deleteUser(id) {
    fetch('/api/users/' + id, {method: 'DELETE'})
        .then(function (response) {
            usersFormDelete.modal('hide');
            usersTable.find('#userRowId\\[' + id + '\\]').remove();
        });
}

function addUserForm() {
    userFormAdd.find('#newUserName').val('');
    userFormAdd.find('#newUserLastName').val('');
    userFormAdd.find('#newPassword').val('');
    userFormAdd.find('#newAge').val('');
    userFormAdd.find('#newEmail').val('');
    fetch('/api/roles').then(function (response) {
        userFormAdd.find('#newRoles').empty();
        response.json().then(roleList => {
            roleList.forEach(role => {
                userFormAdd.find('#newRoles')
                    .append($('<option>').val(role.id).text(role.roleName));
            });
        });
    });
}

function addUser() {
    let txt;
    if (($('#newRoles option:selected').length) === 2) {
        txt = [{
            "id": parseFloat($('#newRoles option[value=1]').val()),
            "roleName": $('#newRoles option[value=1]').text()
        },
            {"id": parseFloat($('#newRoles option[value=2]').val()), "roleName": $('#newRoles option[value=2]').text()}]
    } else if (($('#newRoles option:selected').text()) === "ROLE_ADMIN") {
        txt = [{
            "id": parseFloat($('#newRoles option[value=1]').val()),
            "roleName": $('#newRoles option[value=1]').text()
        },
            {"id": parseFloat($('#newRoles option[value=2]').val()), "roleName": $('#newRoles option[value=2]').text()}]
    } else if (($('#newRoles option:selected').text()) === "ROLE_USER") {
        txt = [{
            "id": parseFloat($('#newRoles option[value=1]').val()),
            "roleName": $('#newRoles option[value=1]').text()
        }]
    }

    let user = {
        'userName': userFormAdd.find('#newUserName').val(),
        'userLastName': userFormAdd.find('#newUserLastName').val(),
        'password': userFormAdd.find('#newPassword').val(),
        'age': userFormAdd.find('#newAge').val(),
        'email': userFormAdd.find('#newEmail').val(),
        'roles': txt
    };
    let request = new Request('/api/users/', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify(user)
    });
    fetch(request)
        .then(function (response) {
            response.json().then(function (userData) {
                loadUsersTable();
            });
        });
}

$(document).ready(function () {
        showAllUsers();
    }
);
