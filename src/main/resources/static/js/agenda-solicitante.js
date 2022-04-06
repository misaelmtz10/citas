const citas = [];
    document.body.onload = async () => {
    try {
        const response = await fetch("/citas/");
        const data = await response.json();
        data.map((cita) => {
            //validar status
            let color;
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
        // console.log(citas);
    } catch (error) {
        console.log(error);
    }
    };

    const calendarRender = (citas) => {
    // console.log(citas);
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
        select: function (args) {
            // console.log(args)
            let date = new Date();
            let dateif = new Date(args.startStr);
            if (dateif < date) {
                Swal.fire({
                    title: 'Agendar Cita',
                    text: 'No se puede agendar la cita en este horario',
                    icon: 'warning',
                    confirmButtonColor: "#009574", confirmButtonText: 'Aceptar'
                })
                
                return;
            }
            let start = args.startStr;
            let end = args.endStr;
            // let today = new Date();
            // let todayfull = today.getFullYear() + '-' + '0' + (today.getMonth() + 1) + '-' +  '0' + today.getDate() +  'T' + today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds();
            // let startChange;
            // console.log(todayfull)
            // console.log(today.getFullYear() + '-' + today.getMonth() + '-' + today.getDay() +  'T' + today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds())

            $("#fechaInicio").val(start);
            $("#fechaFin").val(end);
            $("#fechaInicioHidden").val(start);
            $("#fechaFinHidden").val(end);
            // $("#fechaInicio").attr("min", todayfull);
            // $("#fechaFin").attr("min", todayfull).attr("max", todayfull);
            
            $("#modal-register").modal("show");

            // $("#fechaInicio").change(function(){
            //     startChange = $("#fechaInicio").val();
            //     $("#fechaFin").attr("min", startChange).attr("max", startChange);
            //     console.log(startChange);
            // });
            
        },
        eventClick: function (args) {
            // console.log(args);
            if (args.event._def.extendedProps.item.solicitante.matricula === '20173ti231') {
                let start = args.event._def.extendedProps.item.start;
                let end = args.event._def.extendedProps.item.end;
                let title = args.event._def.extendedProps.item.title;
                let servicio = args.event._def.extendedProps.item.servicio.nombre;
                let ventanilla = args.event._def.extendedProps.item.ventanilla.nombreVentanilla;

                $("#modal-details").modal("show");
                $("#title-details").val(title);
                $("#start-details").val(start.replace(' ' ,'T'));
                $("#end-details").val(end.replace(' ' ,'T'));
                $("#servicio-details").val(servicio);
                $("#ventanilla-details").val(ventanilla);
            } else {
                Swal.fire({
                    title: 'Agendar Cita',
                    text: 'Espacio ocupado',
                    icon: 'info',
                    confirmButtonColor: "#009574", confirmButtonText: 'Aceptar'
                })
            }
            
        },
        editable: false,
        dayMaxEvents: true, // allow "more" link when too many events
        events: citas,
    });
    calendar.render();
};