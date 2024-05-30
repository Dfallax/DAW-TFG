$('.botonEliminarArticulo').click(function (event) {
    const nombre = $(event.target).closest('.span-articulo').find('.productoNombre').text();
    $.ajax({
        type: "get",
        url: "/pedido/articulo/delete/show/" + nombre,
        success: function (htmlRecibido) {
            $('#paraelmodal').html(htmlRecibido);
            $('#modalBorrado').modal('show');
        },
        error: function (e) {
            console.log(e);
        }
    });
});

// Evento para manejar cambios en los inputs
$(document).on('input', '.cantidad', function() {
    cambioInput(this);
});
function cambioInput(input) {
    // Obtener el valor del input
    const valor = parseFloat(input.value);

    // Validar que el valor es un número
    if (!isNaN(valor)) {
        let cantidad = valor;

        const card = input.closest('.span-articulo');
        const nombreProducto = card.querySelector(".productoNombre").innerText;

        // Construimos los parámetros de consulta
        let params = $.param({
            nombreProducto: nombreProducto,
            cantidad: cantidad
        });

        // Construimos la URL de destino
        let urlDestino = "/pedido/actualizar?" + params;
        // Realizamos la solicitud AJAX
        $.ajax({
            type: "GET",
            url: urlDestino,
            success: function (response) {
                $("#contenedorArticulos").html(response);
                // Actualizamos el precio del pedido
                const precios = document.querySelectorAll('.precioArticulo');
                let precioAcumulado =0;
                precios.forEach(precio => {
                    const numero = parseFloat(precio.textContent);
                    precioAcumulado+=numero;
                });
                const precioPedido = parseFloat(precioAcumulado.toFixed(2));
                const elementoPrecioPedido = document.getElementById('precioPedido');
                elementoPrecioPedido.textContent=precioPedido+"€";
            },
            error: function () {
                alert("Error al cargar los productos.");
            }
        });

    } else {
        console.error("El valor del input no es un número válido");
    }
}
