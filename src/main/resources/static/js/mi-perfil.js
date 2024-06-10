$(document).ready(() => {

  $("#idioma").change(function () {
    $.ajax({
      type: "get",
      url: "/usuario/preferencias/idioma",
      data: {
        idioma: $('#idioma').val()
      },
      success: function (data) {
        $('#listaSubtipos').html(data);
      },
      error: function (e) {
        console.log(e);
      }
    });
  });

});