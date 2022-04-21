
var formEdit = document.getElementById('editar');

if (formEdit != null) {

    const alertEdit = (event) => {
        event.preventDefault();
        Swal.fire({
            title: "¿Está seguro?",
            text: "Los datos serán reescritos y almacenados",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#009574", confirmButtonText: 'Aceptar',
            cancelButtonColor: "#6c757d", cancelButtonText: "Cancelar",
        }).then((result) => {
            if (result.isConfirmed) {
                let timerInterval
                Swal.fire({
                    title: 'Reescribiendo datos...',
                    html: 'Colocando corrector en <b></b> archivos....',
                    timer: 2000,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                            b.textContent = Swal.getTimerLeft()
                        }, 100)
                    },
                    willClose: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    /* Read more about handling dismissals below */
                    if (result.dismiss === Swal.DismissReason.timer) {
                        document.getElementById('editar').submit();
                    }
                })
            }
        });
    }

    formEdit.addEventListener('submit', function (event) {
        if (!formEdit.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            alertEdit(event);
        }
        formEdit.classList.add('was-validated');
    }, false);
}


var formEdit2 = document.getElementById('editar2');

if (formEdit2 != null) {
    const alertEdit2 = (event) => {
        event.preventDefault();
        Swal.fire({
            title: "¿Está seguro?",
            text: "Los datos serán reescritos y almacenados",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#009574", confirmButtonText: 'Aceptar',
            cancelButtonColor: "#6c757d", cancelButtonText: "Cancelar",
        }).then((result) => {
            if (result.isConfirmed) {
                let timerInterval
                Swal.fire({
                    title: 'Reescribiendo datos...',
                    html: 'Colocando corrector en <b></b> archivos....',
                    timer: 2000,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                            b.textContent = Swal.getTimerLeft()
                        }, 100)
                    },
                    willClose: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    /* Read more about handling dismissals below */
                    if (result.dismiss === Swal.DismissReason.timer) {
                        document.getElementById('editar2').submit();
                    }
                })
            }
        });
    }

    formEdit2.addEventListener('submit', function (event) {
        if (!formEdit2.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            alertEdit2(event);
        }
        formEdit2.classList.add('was-validated');
    }, false);
}



var formNew = document.getElementById('crear');

if (formNew != null) {

    const alertNew = (event) => {
        event.preventDefault();
        Swal.fire({
            title: "¿Está seguro?",
            text: "Verifique que los datos ingresados son correctos",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#009574", confirmButtonText: 'Aceptar',
            cancelButtonColor: "#6c757d", cancelButtonText: "Cancelar",
        }).then((result) => {
            if (result.isConfirmed) {
                let timerInterval
                Swal.fire({
                    title: 'Guardando datos...',
                    html: 'Introduciendo <b></b> archivos a la fuerza....',
                    timer: 2000,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                            b.textContent = Swal.getTimerLeft()
                        }, 100)
                    },
                    willClose: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    /* Read more about handling dismissals below */
                    if (result.dismiss === Swal.DismissReason.timer) {
                        document.getElementById('crear').submit();
                    }
                })
            }
        });
    }

    formNew.addEventListener('submit', function (event) {
        if (!formNew.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            alertNew(event);
        }
        formNew.classList.add('was-validated');
    }, false);
}


var formNew2 = document.getElementById('crear2');

if (formNew2 != null) {

    const alertNew = (event) => {
        event.preventDefault();
        Swal.fire({
            title: "¿Está seguro?",
            text: "Verifique que los datos ingresados son correctos",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#009574", confirmButtonText: 'Aceptar',
            cancelButtonColor: "#6c757d", cancelButtonText: "Cancelar",
        }).then((result) => {
            if (result.isConfirmed) {
                let timerInterval
                Swal.fire({
                    title: 'Guardando datos...',
                    html: 'Introduciendo <b></b> archivos a la fuerza....',
                    timer: 2000,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                            b.textContent = Swal.getTimerLeft()
                        }, 100)
                    },
                    willClose: () => {
                        clearInterval(timerInterval)
                    }
                }).then((result) => {
                    /* Read more about handling dismissals below */
                    if (result.dismiss === Swal.DismissReason.timer) {
                        document.getElementById('crear2').submit();
                    }
                })
            }
        });
    }

    formNew2.addEventListener('submit', function (event) {
        if (!formNew2.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            alertNew(event);
        }
        formNew2.classList.add('was-validated');
    }, false);
}