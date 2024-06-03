const boton_agregar = document.getElementById('boton-agregar');
const boton_quitar = document.getElementById('boton-quitar');
const cantidad =document.getElementById('cantidad');
const precio_total =document.getElementById('precio-total');
const precio_unitario = document.getElementById('precio-unitario');
const producto_nombre = document.getElementById('nombre-producto')

boton_agregar.addEventListener("click", function () {
    cantidad.textContent=parseInt(cantidad.textContent)+1;
    cambiarPrecioTotal();
});

boton_quitar.addEventListener("click", function () {
    if (cantidad.textContent!=='1')
    cantidad.textContent=parseInt(cantidad.textContent)-1;
    cambiarPrecioTotal();
});

function cambiarPrecioTotal (){
    let producto_cantidad = parseFloat(cantidad.textContent);
    let producto_precio=parseFloat(precio_unitario.textContent);
    precio_total.textContent = producto_cantidad * producto_precio+'';
}

function procesoCompra(){
    if(parseFloat(cantidad.textContent)!= 0){
    window.location.href = `/pedido/producto/compra?nameProduct=${encodeURIComponent(producto_nombre.innerText)}&cantidad=${encodeURIComponent(parseFloat(cantidad.textContent))}`;
    }
}