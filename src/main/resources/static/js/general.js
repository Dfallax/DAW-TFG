
const buscador = document.getElementById('iconoBuscadorNav');
const  elemntMain = document.querySelector('main');
const table_buscadorNav = document.getElementById('table-buscadorNav');
const icono_cerrar_buscador = document.getElementById('icono-cerrar-buscador');
const navbar_zonaIrquierda = document.getElementById('navbar-zonaIrquierda');

// Aquí lo relacionado con la busqueda del nombre
buscador.addEventListener("click", function () {
    // Obtenemos todos los elementos con la clase nav-item
    let elementosNav = document.querySelectorAll(".nav-item");
    // Lo pasamos a un array para poder iterar
    let arrayElementosNav = Array.from(elementosNav);
    arrayElementosNav.forEach(function(elemento){
        if (elemento.id !== "input-buscador") {
            elemento.style.display = "none";
        }else{
            elemento.style.display = "block";
        }
    });
    navbar_zonaIrquierda.style.width="100%";
    //Quitamos el main y lo sustituimos por el de la tabla de nombres de productos
    elemntMain.style.display = "none";
    table_buscadorNav.display = "block";

})
// Cerramos el buscador del nav
icono_cerrar_buscador.addEventListener("click", function () {
    let elementosNav = document.querySelectorAll(".nav-item");
    let arrayElementosNav = Array.from(elementosNav);
    arrayElementosNav.forEach(function(elemento){
        if (elemento.id === "input-buscador") {
            elemento.style.display = "none";
        }else{
            elemento.style.display = "block";
        }
    });
    navbar_zonaIrquierda.style.width="auto";

    elemntMain.style.display = "block"
    table_buscadorNav.style.display = "none"
})

// Después de que se cargue la página cambia el formato de los precios para que cuando por ejmplo es 145.0 ,
// ese cero no aparezca.
document.addEventListener("DOMContentLoaded", function() {
    let elementosPrecio = document.querySelectorAll(".precio");

    // Iterar sobre los elementos y cambiar su contenido
    elementosPrecio.forEach(function(elemento) {
        let precio_anterior = elemento.textContent;
        if (
            precio_anterior.charAt(precio_anterior.length-1)==='0'
        ){
            elemento.textContent = precio_anterior.substring(0, precio_anterior.length-2)
        }
    });
});
// Para prevenir el envío del formulario
document.getElementById('form-buscadorNav').addEventListener('submit', function(event) {
    event.preventDefault();
});

// PARTE DE JQUERY
$(document).ready(() => {
    $("#buscarPorNombre").keyup(function () {
        $.ajax({
            type: "get",
            url: "/producto/lista/filtrada",
            data: {
                nombre: $('#buscarPorNombre').val()
            },
            success: function (htmlRecibido) {
                $('#table-buscadorNav').html(htmlRecibido);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });
});