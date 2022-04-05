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
        select: function (args) {
        let date = new Date();
        let dateif = new Date(args.startStr);
        console.log(dateif)
        if (dateif < date) {
            alert("No se puede seleccionar una fecha pasada");
            return;
        }
        console.log("args: ",args);
        let start = args.startStr;
        let end = args.endStr;
        $("#fechaInicio").val(start);
        $("#fechaFin").val(end);
        $("#modal-register").modal("show");
        // var title = prompt('Event Title:');
        },
        eventClick: function (args) {
            console.log(args);
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