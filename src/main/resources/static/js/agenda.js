let idEvent;
let ventanilla;


const getVentanilla = (param) => {
    ventanilla = param;
}

const citas = [];
    document.body.onload = async () => {
    try {
        const response = await fetch("/citas/");
        const data = await response.json();
        data.map((cita) => {
        //validar status
        let color;
        let dateif = new Date(cita.end);
        switch (cita.estatus) {
            case 1:
                color =  "#00a65a";
                break;
            case 2:
                color =  "#FFA500";
                break;
            case 3:
                color =  "#FF0000";
                break;
            default:
                break;
        }
        
        if (dateif < new Date()) {
            let idCita = parseInt(cita.id);
            color = "#FFA500";  
            fetch(`/citas/cambiar-estatus/${idCita}`); 
        }

        if (cita.ventanilla.id != ventanilla) {
            cita.title = "";
            cita.start = "";
            cita.end = "";
            color = "";

        }
        citas.push({
            id: cita.id,
            title: cita.title,
            start: cita.start,
            end: cita.end,
            color: color,
            item: cita,
        });
        });
        calendarRender(citas);
    } catch (error) {
        console.log(error);
    }
    };

    const calendarRender = (citas) => {
    var calendarEl = document.getElementById("calendar");
    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
        left: "prev,next today",
        center: "title",
        right: "timeGridWeek,timeGridDay,listWeek",
        },
        initialView: "timeGridWeek",
        initialDate: new Date(),
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        nowIndicator: true,
        allDaySlot: false,
        selectMirror: true,
        timeZone: "America/Mexico_City",
        locale: "es",
        hiddenDays: [0, 6],
        slotMaxTime: "18:00",
        slotMinTime: "08:00",
        validRange: {
        start: new Date()
        },
        height:"80%",
        select: function () {
            Swal.fire({
                title: 'Cita',
                text: 'No hay cita registrada',
                icon: 'info',
                confirmButtonColor: "#009574", confirmButtonText: 'Aceptar'
            })
        },
        eventClick: function (args) {
            idEvent = args.event._def.extendedProps.item.id;
            let start = args.event._def.extendedProps.item.start;
            let end = args.event._def.extendedProps.item.end;
            let title = args.event._def.extendedProps.item.title;
            let servicio = args.event._def.extendedProps.item.servicio.nombre;
            let ventanilla = args.event._def.extendedProps.item.ventanilla.nombreVentanilla;
            let solicitante = args.event._def.extendedProps.item.solicitante;
            let data = args.event._def.extendedProps.item.archivo;
            let endif = new Date(end);
            let estatus = args.event._def.extendedProps.item.estatus;
            const btnFinalizar = document.getElementById('btnFinalizar');
            
            $("#title-details").val(title);
            $("#start-details").val(start.replace(' ' ,'T'));
            $("#end-details").val(end.replace(' ' ,'T'));
            $("#servicio-details").val(servicio);
            $("#ventanilla-details").val(ventanilla);
            $("#solicitante-details").val(solicitante.usuario.nombre + ' ' + solicitante.usuario.apellidos);
            $("#matricula-details").val(solicitante.matricula);
            $("#carrera-details").val(solicitante.carrera.nombre);
            $("#correo-details").val(solicitante.usuario.correo);
            $("#documento-details").html(`<a type="*/*" href="http://localhost:8080/file-citas/${data}" target="_blank">Ver</a>`);

            if (endif < new Date() || estatus === 2) {
                btnFinalizar.disabled = true; 
                $("#estatus-details").html('<h5 class="text-muted"><span class="badge badge-pill badge-warning">Finalizada</span></h5>'); 
            }else{
                btnFinalizar.disabled = false;
                $("#estatus-details").html('<h5 class="text-muted"><span class="badge badge-pill badge-success">Activa</span></h5>');
            }

            $("#modal-details").modal("show");
        },
        editable: false,
        dayMaxEvents: true, // allow "more" link when too many events
        events: citas,
    });
    calendar.render();
};


const changeStatus = () => {
    Swal.fire({
        title: 'Finalizar Cita',
        text: '¿Estás seguro de finalizar la cita?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: "#009574", confirmButtonText: 'Aceptar',
        cancelButtonColor: "#6c757d", cancelButtonText: "Cancelar",
        showLoaderOnConfirm: true,
        preConfirm: async () => {
            let id = parseInt(idEvent);
            try {
                const response = await fetch(`/citas/cambiar-estatus/${id}`);
                if (!response.ok) {
                    new Toast({
                        message: 'No se ha podido finalizar la cita',
                        type: 'error'
                    });
                      
                }else{
                    new Toast({
                        message: 'Cita finalizada correctamente',
                        type: 'success'
                        
                    });
                }
                setTimeout(function(){
                    window.location.reload(); 
                },2000)
            } catch (error) {
                Swal.showValidationMessage(
                    `Request failed: ${error}`
                );
            }
        },
    });

    
}