package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.entidades.Producto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DBRider
public class ProductoRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    private static final Long ID_PRODUCTO = 1L;

    @Test
    @DataSet({"datasets/categorias.yml", "datasets/subcategorias.yml"})
    void shouldFindProductsByPartialNameIgnoreCase() {
        Producto p = Producto.builder()
                .nombre("Ibérica")
                .precio(145)
                .descripcion("""
                         Cesta muy "nuestra". Con sobre de jamón de bellota 100%, en tapas, bote de aceitunas rellenas,
                         bolsa de patatas fritas con aceite de oliva, picos de pan, bote de cóctel de frutos secos,
                         botella de aceite de oliva Vigen Extra con D.O. Jaén, tomates cherry en ramo y burrito de
                         esparto artesanal. Flores silvestres que durarán mucho tiempo porque secan perfectas.

                        Las imágenes son orientativas el atrezo floral puede variar en colores o variedad.""")
                .foto("http://localhost:9000/files/cesta1.png")
                .categoria(categoriaRepository.findByNombre("cestas").orElse(null))
                .subcategoria(subcategoriaRepository.findByNombre("Navidad").orElse(null))
                .build();
        productoRepository.save(p);
        List<Producto> productos = productoRepository.findByNombreContainsIgnoreCase("Ibérica");
        int numProductos = 1;
        assertThat(productos).hasSize(numProductos);

    }


    @Test
    @DataSet({"datasets/categorias.yml", "datasets/subcategorias.yml"})
    void shouldPersistAndRetrieveProductWithCategoryAndSubcategory() {
        Categoria cestas = em.find(Categoria.class, 1L);

        Producto producto = Producto.builder()
                .nombre("Primavera")
                .precio(87.5)
                .descripcion("""
                        Fresón, nísperos, albaricoques, uvas, naranja, ciruelas, nectarina, manzana, pera, \
                        plátanos, clementina,? Una selección de frutas de temporada. En un capazo de palma.""")
                .foto("http://localhost:9000/files/cesta3.jpg")
                .categoria(cestas)
                .subcategoria(subcategoriaRepository.findByNombre("Navidad").orElse(null))
                .build();
        log.info("Producto estado transient {}", producto);
        em.persist(producto);

        log.info("Producto estado managed después de persist {}", producto);
        assertThat(producto.getId()).isNotNull();

        Producto productoEnContexto = em.find(Producto.class, producto.getId());

        log.info("Producto en contexto {}", productoEnContexto);
        assertThat(productoEnContexto).isEqualTo(producto);

        // Hacer la prueba a) sin la línea siguiente y b) con la setNombre....
        producto.setNombre("Paco");
        em.flush();

        log.info("Producto después de flush {}", producto);

    }

    @Test
    @DataSet({"datasets/producto.yml", "datasets/categorias.yml", "datasets/subcategorias.yml"})
    void shouldRemoveProductAndFlushChanges() {
        Producto productoBorrar = em.find(Producto.class, 1L);
        em.remove(productoBorrar);
        log.info("Producto después de remove {}", productoBorrar);
        // Comprobar que al eliminarse del contexto pasa a estado removed,
        // pero todavía o está en BD
        assertThat(em.find(Producto.class, 1L)).isNull();
        // Con el flush se produce el delete en la BD
        em.flush();

    }


    @Test
    @DataSet(value = {"datasets/categorias.yml", "datasets/producto.yml"})
    void shouldRemoveAndPersistAfterFlush() {
        Producto productoBorrar = em.find(Producto.class, 1L);
        em.remove(productoBorrar);
        log.info("Producto después de remove {}", productoBorrar);

        assertThat(em.find(Producto.class, 1L)).isNull();
        // Aquí, al hacer persist después de remove devuelve el objeto al estado managed,
        // es decir, se anula el borrado y no se genera el delete en la BD tras el flush
        em.persist(productoBorrar);
        em.flush();
        // Este assert confirma que el producto sigue en la BD
        assertThat(em.find(Producto.class, 1L)).isNotNull();

    }

    @Test
    @DataSet({"datasets/producto.yml", "datasets/categorias.yml", "datasets/subcategorias.yml"})
    void shouldDetachMergeAndFlushProducto() {
        Producto productoDetached = em.find(Producto.class, 1L);
        productoDetached.setNombre("Nuevo nombre");
        em.detach(productoDetached);
        em.flush();

        log.info("Producto después de detach + flush {}", productoDetached);
        // Confirmar que el producto en la BD todavía tiene el nombre de Nuevo nombre
        assertThat(
                em.find(Producto.class, 1L).getNombre()
        ).isEqualTo("Producto de prueba");
        //Despues del merge si se produce el cambio del nombre
        Producto productoManaged = em.merge(productoDetached);
        log.info("Producto detached después de merge {} {}", productoDetached.hashCode(), productoDetached);
        log.info("Producto managed después de merge {} {} ", productoManaged.hashCode(), productoManaged);
        // La llamada a merge devuelve un objeto managed distinto al desligado pero con el mismo contenido.
        assertThat(productoManaged).isNotEqualTo(productoDetached);
        assertThat(productoManaged.getNombre().equals("Nuevo nombre")).isEqualTo(productoDetached.getNombre().equals("Nuevo nombre"));

        em.flush();
        // Al hacer flush, los cambios en la entidad managed, y atención,
        // que fueron realizados en el objeto desligado, se persistirán con el consabido UPDATE.
        log.info("Flush después del merge");
        // Confirmar que la mascota en la BD ahora tiene valor false
        assertThat(em.find(Producto.class, 1L).getNombre()).isEqualTo("Nuevo nombre");
    }
}
