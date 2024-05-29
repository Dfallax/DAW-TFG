function actualiarPreciosFormato(){
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
}

//-----------------------------------JQUERY---------------------------------/
$(document).ready(function () {
    function initNoUiSlider(minPrice, maxPrice) {
        const priceSlider = document.getElementById('price-range');
        if (priceSlider) {
            console.log("Valores de precio recibidos:", minPrice, maxPrice);

            // Asegurarse de que minPrice y maxPrice sean números
            if (isNaN(minPrice)) {
                minPrice = 0; // Valor por defecto
            }
            if (isNaN(maxPrice)) {
                maxPrice = 1000; // Valor por defecto
            }

            // Asegurarse de que minPrice sea menor que maxPrice
            if (minPrice > maxPrice) {
                let temp = minPrice;
                minPrice = maxPrice;
                maxPrice = temp;
            }

            // Inicializar noUiSlider
            noUiSlider.create(priceSlider, {
                start: [minPrice, maxPrice], // Valores iniciales
                connect: true,
                range: {
                    'min': minPrice,
                    'max': maxPrice
                },
                step: 0.5,
                format: {
                    to: function (value) {
                        return value.toFixed(2);
                    },
                    from: function (value) {
                        return Number(value);
                    }
                }
            });

            // Sincronizar el control deslizante con los inputs numéricos
            const priceMinInput = document.getElementById('price-min');
            const priceMaxInput = document.getElementById('price-max');

            priceSlider.noUiSlider.on('update', function (values, handle) {
                if (handle === 0) {
                    priceMinInput.value = values[handle];
                } else {
                    priceMaxInput.value = values[handle];
                }
            });

            priceMinInput.addEventListener('change', function () {
                priceSlider.noUiSlider.set([this.value, null]);
            });

            priceMaxInput.addEventListener('change', function () {
                priceSlider.noUiSlider.set([null, this.value]);
            });
        } else {
            console.error('priceSlider no está definido');
        }
    }

    // Mostrar el modal de Filtrar
    $('#aOpcionesDerecha').click(function (event) {
        // Obtener los valores actuales de tipo, subtipo y orden
        let tipoProducto = $("#tituloTipoProducto").text().toLowerCase();
        let subtipo = "all";
        $(".buttonSubtipo").each(function () {
            if ($(this).css("background-color") === "rgb(0, 101, 55)") { // CSS en RGB
                subtipo = $(this).text();
            }
        });
        let orden = 0; // Valor predeterminado si no hay ninguno seleccionado
        $("[id^='flexRadioDefault']").each(function () {
            if ($(this).is(":checked")) {
                orden = $(this).attr("id").slice(-1);
            }
        });

        // Construir los parámetros de consulta
        let params = $.param({
            tipo: tipoProducto,
            subtipo: subtipo,
            orden: orden
        });

        $.ajax({
            type: "get",
            url: "/producto/filtrar/show?" + params,
            success: function (htmlRecibido) {
                console.log('Contenido del modal cargado'); // Depuración
                $('#paraModalOpcionesFiltrar').html(htmlRecibido);

                // Inicializar noUiSlider después de cargar el modal
                $('#opcionesFiltrar').on('shown.bs.modal', function () {
                    console.log('Modal mostrado, inicializando noUiSlider...'); // Depuración

                    // Obtener los valores de precio mínimo y máximo del modal recién cargado
                    const minPrice = parseFloat($('#price-min').val()) || 0;
                    const maxPrice = parseFloat($('#price-max').val()) || 1000;

                    initNoUiSlider(minPrice, maxPrice);
                });

                $('#opcionesFiltrar').modal('show');
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

    // Muestra el modal de Ordenar
    $('#aOpcionesIzquierda').click(function (event) {
        $.ajax({
            type: "get",
            url: "/producto/ordenar/show",
            success: function (htmlRecibido) {
                $('#paraModalOpcionesOrdenar').html(htmlRecibido);
                $('#opcionesOrdenar').modal('show');
                // Recuperar el estado del radio botón seleccionado
                let selectedRadioId = localStorage.getItem('selectedRadioId');
                if (selectedRadioId) {
                    $("#" + selectedRadioId).prop("checked", true);
                }
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

    // El evento clic para #enviarOrdenar se adjunta usando la delegación de eventos a document, que
    // siempre está presente.
    $(document).on("click", "#enviarOrdenar", function () {
        // Obtenemos el tipo
        let tipoProducto = $("#tituloTipoProducto").text().toLowerCase();
        // Obtenemos el subtipo
        let subtipo = "all";
        $(".buttonSubtipo").each(function () {
            if ($(this).css("background-color") === "rgb(0, 101, 55)") {  // CSS en RGB
                subtipo = $(this).text();
            }
        });
        // Obtenemos el orden basado en el input seleccionado
        let orden = 0;  // Valor predeterminado si no hay ninguno seleccionado
        let selectedRadioId = null; // Para almacenar el ID del radio seleccionado
        $("[id^='flexRadioDefault']").each(function () {
            if ($(this).is(":checked")) {
                // Obtener el último carácter del ID
                orden = $(this).attr("id").slice(-1);
                selectedRadioId = $(this).attr("id"); // Guardamos el ID del radio seleccionado

                // Almacenar el estado seleccionado en localStorage
                localStorage.setItem('selectedRadioId', selectedRadioId);
            }
        });
        // Construimos los parámetros de consulta
        let params = $.param({
            tipo: tipoProducto,
            subtipo: subtipo,
            orden: orden
        });

        // Construimos la URL de destino
        let urlDestino = "/producto/ordenar?" + params;

        //Realizamos la solicitud AJAX
        $.ajax({
            type: "GET",
            url: urlDestino,
            success: function (response) {
                // Reemplazar el contenido del contenedor de productos con la respuesta
                $("#contenedorProductos").html(response);
                // Volver a seleccionar el radio previamente seleccionado
                if (selectedRadioId) {
                    $("#" + selectedRadioId).prop("checked", true);
                }
                // Otros cambios
                actualiarPreciosFormato();
            },
            error: function () {
                alert("Error al cargar los productos.");
            }
        });
    });

    $(document).on("click", "#enviarFiltrar", function () {
        // Obtener los valores del filtro
        let tipoProducto = $("#tituloTipoProducto").text().toLowerCase();
        let subtipo = "all";
        $(".buttonSubtipo").each(function () {
            if ($(this).css("background-color") === "rgb(0, 101, 55)") { // CSS en RGB
                subtipo = $(this).text();
            }
        });
        let orden = 0; // Valor predeterminado si no hay ninguno seleccionado
        $("[id^='flexRadioDefault']").each(function () {
            if ($(this).is(":checked")) {
                orden = $(this).attr("id").slice(-1);
            }
        });

        // Obtener los valores de precio mínimo y máximo
        let precioMin = $('#price-min').val();
        let precioMax = $('#price-max').val();

        // Construir los parámetros de consulta
        let params = $.param({
            tipo: tipoProducto,
            subtipo: subtipo,
            orden: orden,
            precioMin: precioMin,
            precioMax: precioMax
        });

        // Realizar la solicitud AJAX
        $.ajax({
            type: "GET",
            url: "/producto/filtrar?" + params,
            success: function (response) {
                // Reemplazar el contenido del contenedor de productos con la respuesta
                $("#contenedorProductos").html(response);
                // Cerrar el modal de filtrar
                $('#opcionesFiltrar').modal('hide');
            },
            error: function () {
                alert("Error al filtrar los productos.");
            }
        });
    });


    // Carga de lista de todos los productos del subtipo al que pertenece
    $(".buttonSubtipo").click(function () {
        let tipoProducto = $("#tituloTipoProducto").text().toLowerCase();
        let subtipoProducto = $(this).text();

        let urlDestino = "/producto/tipo/" + tipoProducto + "/" + subtipoProducto;

        // Realizamos la solicitud AJAX
        $.ajax({
            type: "GET",
            url: urlDestino,
            success: function (response) {
                // Reemplazar el contenido del contenedor de productos con la respuesta
                $("#contenedorProductos").html(response);
                $(".buttonSubtipo").css({
                    "background-color": "#ffffff",
                    "color": "#000000"
                }); // Resetea el color de fondo de todos los botones
                $(this).css({
                    "background-color": "#006537",
                    "color": "#FFFFFF"
                }); // Cambia el color de fondo del botón clicado

                actualiarPreciosFormato()

            }.bind(this), // Utiliza bind para mantener el contexto de "this"
            error: function () {
                alert("Error al cargar los productos.");
            }
        });
    });
});
