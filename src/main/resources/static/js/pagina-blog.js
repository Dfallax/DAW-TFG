// Para prevenir el envío del formulario
document.getElementById('form-buscadorBlog').addEventListener('submit', function(event) {
    event.preventDefault();
});

// Para prevenir el envío del formulario
document.getElementById('form-buscadorBlog').addEventListener('submit', function(event) {
    event.preventDefault();
});

$(document).ready(() => {
    $("#buscarPorTitulo").off('keyup').on('keyup', function () {
        $.ajax({
            type: "get",
            url: "/blog/lista/filtrada",
            data: {
                titulo: $('#buscarPorTitulo').val()
            },
            success: function (htmlRecibido) {
                $('#seccion-blog').html($(htmlRecibido));
            },
            error: function (e) {
                console.log(e);
            }
        });
    });
});

