
var formEdit = document.getElementById('editar');

if(formEdit != null){

    const alertEdit = (event) => {
        event.preventDefault();
        Swal.fire({
            title: "¿Esta seguro?",
            text: "Los datos seran reescritos y almacenados",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#6c757d",
            confirmButtonText: "Actualizar"
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



var formNew = document.getElementById('crear');

if(formNew != null){

    const alertNew = (event) => {
        event.preventDefault();
        Swal.fire({
            title: "¿Esta seguro?",
            text: "Verifique que los datos ingresados son correctos",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#009c80",
            cancelButtonColor: "#6c757d",
            confirmButtonText: "Guardar"
        }).then((result) => {
            if (result.isConfirmed) {
                let timerInterval
                Swal.fire({
                    title: 'Guardando datos...',
                    html: 'Metiendo <b></b> archivos a la fuerza....',
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

