let idEvent;
const citas = [];
    document.body.onload = async () => {
    try {
        const response = await fetch("/citas/");
        const data = await response.json();
        data.map((cita) => {
        //validar status
        citas.push({
            id: cita.id,
            title: cita.title,
            start: cita.start,
            end: cita.end,
            color: "#00a65a",
            item: cita,
        });
        });
        calendarRender(citas);
        console.log(citas);
    } catch (error) {
        console.log(error);
    }
    };

    const calendarRender = (citas) => {
    console.log(citas);
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
        select: function () {
            Swal.fire({
                title: 'Cita',
                text: 'No hay cita registrada',
                icon: 'info',
                confirmButtonColor: "#009574", confirmButtonText: 'Aceptar'
            })
            // let date = new Date();
            // let dateif = new Date(args.startStr);
            // console.log(dateif)
            // if (dateif < date) {
            //     Swal.fire({
            //         title: 'Agendar Cita',
            //         text: 'No se puede agendar la cita en este horario',
            //         icon: 'warning',
            //         confirmButtonColor: "#009574", confirmButtonText: 'Aceptar'
            //     })
                
            //     return;
            // }
            // let start = args.startStr;
            // let end = args.endStr;
            // $("#fechaInicio").val(start);
            // $("#fechaFin").val(end);
            // $("#modal-register").modal("show");
            // var title = prompt('Event Title:');
        },
        eventClick: function (args) {
            console.log(args);
            idEvent = args.event._def.extendedProps.item.id;
            let start = args.event._def.extendedProps.item.start;
            let end = args.event._def.extendedProps.item.end;
            let title = args.event._def.extendedProps.item.title;
            let servicio = args.event._def.extendedProps.item.servicio.nombre;
            let ventanilla = args.event._def.extendedProps.item.ventanilla.nombreVentanilla;
            // console.log("start: ", start.replace(' ' ,'T'))
            $("#modal-details").modal("show");
            $("#title-details").val(title);
            $("#start-details").val(start.replace(' ' ,'T'));
            $("#end-details").val(end.replace(' ' ,'T'));
            $("#servicio-details").val(servicio);
            $("#ventanilla-details").val(ventanilla);
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
        cancelButtonColor: "#DD6B55", cancelButtonText: "Cancelar",
        showLoaderOnConfirm: true,
        preConfirm: async (idEvent) => {
            try {
                const response = await fetch(`/citas/cambiar-estatus/${6}`);
                if (!response.ok) {
                    throw new Error(response.statusText);
                }
                return await response.json();
            } catch (error) {
                Swal.showValidationMessage(
                    `Request failed: ${error}`
                );
            }
        },
    });

    
}