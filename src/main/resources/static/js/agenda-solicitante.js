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
                color = "#FFA500";
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
        selectMirror: false,
        timeZone: "America/Mexico_City",
        locale: "es",
        hiddenDays: [0, 6],
        slotMaxTime: "17:30",
        slotMinTime: "08:00",
        validRange: {
        start: new Date(),
        },
        height:"80%",
        select: function (args) {
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
            
            $("#fechaInicio").val(start);
            $("#fechaFin").val(end);
            $("#fechaInicioHidden").val(start);
            $("#fechaFinHidden").val(end);   
            $("#modal-register").modal("show");
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

const alert = (event) =>{
    event.preventDefault();
    Swal.fire({
        title: 'Agendar Cita',
        text: '¿Estás seguro de agendar la cita?',
        icon: 'info',
        showCancelButton: true,
        confirmButtonColor: "#009574", confirmButtonText: 'Aceptar',
        cancelButtonColor: "#DD6B55", cancelButtonText: "Cancelar",     
    })
}

var forms = document.querySelectorAll('.needs-validation');
Array.prototype.slice.call(forms)
.forEach(function (form) {
    form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation(); 
        }else{
            alert(event);
        }
        form.classList.add('was-validated');
    }, false);
});
    
function validate(){
    setTimeout(function(){ }, 500);
    $(function(){
        $('.swal2-confirm').on("click",function(){
            document.getElementById('formRegister').submit();
        });
    });
}

