let matricula;

const getMatricula = (param) => {
    matricula = param;
}

const getServicio = async (idServicio) => {
    let id = parseInt(idServicio);
    const response = await fetch(`/servicios/getServicio/${id}`)
    const data = await response.json();
    let list = data;
    let content = "";
    if (list.length > 0) {
        content += `
            <div class="alert alert-primary" role="alert">
                Para hacer válido tu trámite, al anexar el archivo deberás contemplar los siguientes documentos:
            <br/>
            <ul>`
                for (let index = 0; index < list.length; index++) {
                    content += `<li><b>${list[index]}</b></li>`
                    
                }
            content += `
            </ul>
            <span class="text-wrap"><b>Nota:</b> Es importante anexar todos los documentos requeridos en un solo archivo (PDF), 
            nombrarlo sin espacios y/o caracteres especiales a excepción del guión medio y bajo.</span>
            </div>
        `;
        $("#mostrarDocs").html(content);
    }
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
                color = "#FFA500";
            }

            if (cita.solicitante.matricula != matricula) {
                cita.title = "Espacio ocupado";
                color = "#787772";
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
            let timeDiff = new Date(end).getTime() - new Date(start).getTime();
            let hours = timeDiff / (1000*60*60);

            if (hours > 0.5) {
                Swal.fire({
                    title: 'Agendar Cita',
                    text: 'La cita tiene una duración de 30 minutos por defecto',
                    icon: 'warning',
                    confirmButtonColor: "#009574", confirmButtonText: 'Aceptar'
                })
            }else{
                $("#fechaInicio").val(start);
                $("#fechaFin").val(end);
                $("#fechaInicioHidden").val(start);
                $("#fechaFinHidden").val(end);   
                $("#modal-register").modal("show");
            }
        },
        eventClick: function (args) {
            if (args.event._def.extendedProps.item.solicitante.matricula === matricula) {
                let start = args.event._def.extendedProps.item.start;
                let end = args.event._def.extendedProps.item.end;
                let title = args.event._def.extendedProps.item.title;
                let servicio = args.event._def.extendedProps.item.servicio.nombre;
                let ventanilla = args.event._def.extendedProps.item.ventanilla.nombreVentanilla;
                let estatus = args.event._def.extendedProps.item.estatus;
                let data = args.event._def.extendedProps.item.archivo;
                
                if (estatus === 1) {
                    $("#estatus-details").html('<h5 class="text-muted"><span class="badge badge-pill badge-success">Activa</span></h5>')
                } else {
                    $("#estatus-details").html('<h5 class="text-muted"><span class="badge badge-pill badge-warning">Finalizada</span></h5>')
                }
                
                $("#title-details").val(title);
                $("#start-details").val(start.replace(' ' ,'T'));
                $("#end-details").val(end.replace(' ' ,'T'));
                $("#servicio-details").val(servicio);
                $("#ventanilla-details").val(ventanilla);
                $("#documento-details").html(`<a type="*/*" href="http://localhost:8080/file-citas/${data}" target="_blank">Ver</a>`);
                $("#modal-details").modal("show");
                
            }else{
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
        cancelButtonColor: "#6c757d", cancelButtonText: "Cancelar",     
    }).then((result) => {
        if (result.isConfirmed) {
            let timerInterval
            Swal.fire({
                title: 'Guardando datos...',
                html: 'Enviando <b></b> archivos a la ventanilla para revisión....',
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
                    document.getElementById('formRegister').submit();
                }
            })
        }
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

        });
    });
}


let cleanFields = () =>{
    document.getElementById('title').value = "";
    document.getElementById('idServicio').value = "";
    document.getElementById('idVentanilla').value = "";
    document.getElementById('inputGroupFile01').value = "";
    $("#mostrarDocs").html('');
    const forms = document.querySelectorAll(".needs-validation");
    Array.prototype.slice.call(forms).forEach((form) => {
    form.classList.remove("was-validated");
    });
}
