package com.luislr.zerif.componentes;

import com.luislr.zerif.entidades.*;
import com.luislr.zerif.servicios.*;
import com.luislr.zerif.storage.StorageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class InitData {

    private final StorageService storageService;
    private final PerfilService perfilService;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;
    private final ProductoService productoService;
    private final ValoracionProductoService valoracionProductoService;
    private final SeccionBlogService seccionBlogService;
    private final PedidoService pedidoService;
    private final ArticuloPedidoService articuloPedidoService;
    private final DireccionService direccionService;
    private final TarjetaService tarjetaService;
    private final ReceptorService receptorService;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initStorage();
        initUsuarios();
        initSeccionBlog();
        initPreferencias();
        initProducto();
        initPedidos(); // Añadimos la llamada al método initPedidos
    }

    public void initStorage() {
        storageService.init();
        storageService.loadAll();
    }

    public void initUsuarios() {
        // Perfiles
        Perfil perfilUser = Perfil.builder().nombre("ROLE_USER").build();
        Perfil perfilAdmin = Perfil.builder().nombre("ROLE_ADMIN").build();

        perfilService.save(perfilUser);
        perfilService.save(perfilAdmin);

        // Crear 10 usuarios de tipo 'USER'
        for (int i = 1; i <= 10; i++) {
            Usuario usuario = Usuario.builder()
                    .username("user" + i)
                    .email("user" + i + "@gmail.com")
                    .password("user" + i)
                    .perfil(perfilUser)
                    .nombre("nombre"+i)
                    .apellidos("apellidos"+i)
                    .telefono("612346124")
                    .build();
            usuarioService.save(usuario);
        }

        // Usuario tipo 'ADMIN'
        Usuario admin = Usuario.builder()
                .username("luis")
                .email("luisn@gmail.com")
                .password("luis")
                .perfil(perfilAdmin)
                .build();
        usuarioService.save(admin);
    }

    public void initPreferencias() {
        List<Usuario> usuarios = usuarioService.findAll();

        for (Usuario usuario : usuarios) {
            Preferencias prefs = Preferencias.builder()
                    .idioma("es_ES") // Puedes asignar un idioma por defecto o personalizarlo
                    .usuario(usuario)
                    .build();
            em.persist(prefs);
        }
    }

    public void initProducto() {
        // Categorias
        Categoria cesta = Categoria.builder().nombre("cestas").build();
        Categoria ramo = Categoria.builder().nombre("ramos").build();
        Categoria flor = Categoria.builder().nombre("flores").build();
        Categoria planta = Categoria.builder().nombre("plantas").build();
        Categoria evento = Categoria.builder().nombre("eventos").build();

        categoriaService.saveAll(Arrays.asList(cesta, ramo, flor, planta, evento));

        // Subcategorías
        List<Subcategoria> cestas = Arrays.asList(
                Subcategoria.builder().nombre("Cestas de Navidad").categoria(cesta).build(),
                Subcategoria.builder().nombre("Cestas del Dia de la Madre").categoria(cesta).build(),
                Subcategoria.builder().nombre("Cestas de Bodas").categoria(cesta).build()
        );

        List<Subcategoria> flores = Arrays.asList(
                Subcategoria.builder().nombre("Flores de primavera").categoria(flor).build(),
                Subcategoria.builder().nombre("Orquídeas").categoria(flor).build()
        );

        List<Subcategoria> plantas = Arrays.asList(
                Subcategoria.builder().nombre("Exterior").categoria(planta).build(),
                Subcategoria.builder().nombre("Interior").categoria(planta).build()
        );

        subcategoriaService.saveAll(cestas);
        subcategoriaService.saveAll(plantas);
        subcategoriaService.saveAll(flores);

        // Producto de prueba
        Producto p8 = Producto.builder()
                .nombre("Amazonas")
                .precio(250)
                .descripcion("""
                        Una gran cesta con todo tipo de variedad de frutas, piñas, uvas, mango, plátanos, papaya, granadilla, bananitos, ciruelas, fresas, frambuesas, moras, melón, chirimoya, aguacate, physalis?, y tableta de chocolate orgánico. Arreglo de flores exóticas y verdes.

                        Las frutas se reciben diariamente y están seleccionadas una a una por personal cualificado.

                        La bandeja es de metal, cuadrada y perfecta para utilizarla muchas veces.

                        Si este regalo te parece que puede ser aún más espectacular, dínoslo y te preparamos una cesta más grande, a tu medida.

                        Puedes darle más valor a tu regalo complementando la cesta con una botella de vino o champán u otros complementos.""")
                .foto("http://localhost:9000/files/cesta4.jpg")
                .categoria(categoriaService.findByNombre("cestas").orElseThrow())
                .subcategoria(subcategoriaService.findByNombre("Cestas de Bodas").orElse(null))
                .build();
        productoService.save(p8);

        // Filtrar las subcategorías de la categoría "cesta"
        List<Subcategoria> subcategoriasCestas = cestas.stream()
                .filter(subcategoria -> subcategoria.getCategoria().equals(cesta))
                .toList();
        // Filtrar las subcategorías de la categoría "flor"
        List<Subcategoria> subcategoriasFlores = flores.stream()
                .filter(subcategoria -> subcategoria.getCategoria().equals(flor))
                .toList();

        // Crear 29 productos de tipo cesta con subcategorías de cestas
        Random random = new Random();
        for (int i = 1; i <= 30; i++) {
            Subcategoria subcategoria = subcategoriasCestas.get((i - 1) % subcategoriasCestas.size()); // Seleccionar subcategoria ciclicamente

            // Generar un precio aleatorio entre 50 y 250
            double precio = 50 + (200 * random.nextDouble());

            precio = Math.round(precio * 100.0) / 100.0;

            Producto producto = Producto.builder()
                    .nombre("Cesta " + i)
                    .precio(precio) // Precio aleatorio
                    .descripcion("Descripción del producto cesta " + i)
                    .foto("http://localhost:9000/files/cesta" + (i % 5 + 1) + ".jpg") // Variar entre 5 imágenes
                    .categoria(cesta) // Categoría "cestas"
                    .subcategoria(subcategoria)
                    .build();
            productoService.save(producto);
        }

        // Crear 30 productos de tipo ramo con subcategorías de ramos
        for (int i = 1; i <= 30; i++) {

            // Generar un precio aleatorio entre 50 y 250
            double precio = 50 + (200 * random.nextDouble());
            // Redondear a dos decimales
            precio = Math.round(precio * 100.0) / 100.0;

            Producto producto = Producto.builder()
                    .nombre("Ramo " + i)
                    .precio(precio) // Precio aleatorio redondeado a dos decimales
                    .descripcion("Descripción del producto ramo " + i)
                    .foto("http://localhost:9000/files/ramo" + (i % 5 + 1) + ".webp") // Variar entre 5 imágenes
                    .categoria(ramo) // Categoría "ramos"
                    .build();
            productoService.save(producto);
        }

        // Crear 30 productos de tipo flor con subcategorías de flores
        for (int i = 1; i <= 31; i++) {
            Subcategoria subcategoria = subcategoriasFlores.get((i - 1) % subcategoriasFlores.size()); // Seleccionar subcategoria ciclicamente

            double precio = 50 + (200 * random.nextDouble());

            precio = Math.round(precio * 100.0) / 100.0;

            Producto producto = Producto.builder()
                    .nombre("Flor " + i)
                    .precio(precio) // Precio aleatorio
                    .descripcion("Descripción del producto flor " + i)
                    .foto("http://localhost:9000/files/flor" + (i % 5 + 1) + ".webp") // Variar entre 5 imágenes
                    .categoria(flor) // Categoría "cestas"
                    .subcategoria(subcategoria)
                    .build();
            productoService.save(producto);
        }

        // Crear productos personalizados para eventos
        Producto evento1 = Producto.builder()
                .nombre("Decoración de Boda Elegante")
                .precio(1500)
                .descripcion("""
                        Transforma tu boda en un evento de ensueño con nuestra decoración elegante. Este servicio incluye:
                        - Arreglos florales personalizados.
                        - Centros de mesa con diseño exclusivo.
                        - Iluminación ambiental para crear una atmósfera romántica.
                        - Telones y cortinas de alta calidad.
                        Nuestro equipo de expertos se encargará de cada detalle para que tú solo disfrutes de tu día especial.""")
                .foto("http://localhost:9000/files/decoracion_boda_elegante.webp")
                .categoria(evento)
                .build();
        productoService.save(evento1);

        Producto evento2 = Producto.builder()
                .nombre("Fiesta Temática Infantil")
                .precio(800)
                .descripcion("""
                        Haz realidad los sueños de los más pequeños con nuestra fiesta temática infantil. Este servicio incluye:
                        - Decoración temática basada en el personaje o tema favorito del niño.
                        - Arcos de globos y pancartas personalizadas.
                        - Mesas de dulces y postres decoradas con el tema elegido.
                        - Animación y juegos organizados por nuestro equipo.
                        Hacemos que cada celebración sea única y memorable.""")
                .foto("http://localhost:9000/files/fiesta_tematica_infantil.webp")
                .categoria(evento)
                .build();
        productoService.save(evento2);

        // Obtener todos los usuarios
        List<Usuario> usuarios = usuarioService.findAll();
        List<Producto> productos = productoService.findAll();

        // Crear valoraciones para cada producto por cada usuario
        for (Producto producto : productos) {
            for (Usuario usuario : usuarios) {
                int valoracion = (int) (Math.random() * 10) + 1;
                ValoracionProducto vp = ValoracionProducto.builder()
                        .producto(producto)
                        .usuario(usuario)
                        .valoracion(valoracion)
                        .id(ValoracionUsuarioPK.builder().usuarioId(usuario.getId()).productoId(producto.getId()).build())
                        .build();
                valoracionProductoService.save(vp);
            }
        }
    }

    public void initSeccionBlog() {
        Usuario usuarioAdmin = usuarioService.findByUsername("luis");

        List<SeccionBlog> secciones = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            SeccionBlog seccionBlog = SeccionBlog.builder()
                    .titulo("Título de la entrada " + i)
                    .descripcion("Descripción del blog número " + i )
                    .video("https://www.youtube.com/embed/eG5ur8R_FIs?si=Y5tAqfIvLMdWrWyP")
                    .fechSubida(LocalDate.now().minusDays(i))
                    .usuario(usuarioAdmin)
                    .build();
            secciones.add(seccionBlog);
        }

        seccionBlogService.saveAll(secciones);
    }

    public void initPedidos() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<Producto> productos = productoService.findAll();
        Random random = new Random();

        for (Usuario usuario : usuarios) {
            if (!usuario.getPerfil().getNombre().equals("ROLE_ADMIN")) { // Excluimos usuarios admin
                for (int i = 0; i < 5; i++) {

                    Pedido.EstadoPedido estado = (i == 0) ? Pedido.EstadoPedido.CARRITO : Pedido.EstadoPedido.COMPLETADO;
                    Pedido pedido = Pedido.builder()
                            .estado(estado)
                            .usuario(usuario)
                            .build();

                    pedidoService.save(pedido); // Guardamos el pedido primero para obtener el ID
                    if (pedido.getEstado()!= Pedido.EstadoPedido.CARRITO) {
                        Direccion direccion = Direccion.builder()
                                .calle("Calle " + random.nextInt(100))
                                .numero(random.nextInt(100))
                                .piso(random.nextInt(10))
                                .puerta((char) (random.nextInt(26) + 'A'))
                                .ciudad("Ciudad " + random.nextInt(100))
                                .pedido(pedido)
                                .build();
                        direccionService.save(direccion); // Guardar dirección

                        Tarjeta tarjeta = Tarjeta.builder()
                                .numTarjeta("4111111111111111")
                                .cv("123")
                                .fechCaducidad(YearMonth.now().plusYears(3))
                                .pedido(pedido)
                                .build();
                        tarjetaService.save(tarjeta);

                        Receptor receptor = Receptor.builder()
                                .nombre(usuario.getNombre())
                                .apellidos(usuario.getApellidos())
                                .telefono(usuario.getTelefono())
                                .pedido(pedido)
                                .build();
                        receptorService.save(receptor);

                        pedido.setDireccion(direccion);
                        pedido.setTarjeta(tarjeta);
                        pedido.setReceptor(receptor);
                        pedidoService.save(pedido);
                    }
                    // Agregar artículos al pedido después de guardar el pedido para obtener su ID
                    for (int j = 0; j < 5; j++) {
                        Producto producto = productos.get(random.nextInt(productos.size()));
                        ArticuloPedidoPK id = new ArticuloPedidoPK(pedido.getId(), producto.getId());
                        ArticuloPedido articulo = ArticuloPedido.builder()
                                .id(id)
                                .pedido(pedido)
                                .producto(producto)
                                .precioUnidad(BigDecimal.valueOf(producto.getPrecio()))
                                .cantidad(random.nextInt(5) + 1)
                                .build();
                        articuloPedidoService.save(articulo);
                    }
                }
            }
        }
    }

}
