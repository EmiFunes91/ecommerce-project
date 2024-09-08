package com.ecommerce.service;

import com.ecommerce.model.Cupon;
import com.ecommerce.model.DetallePedido;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CuponService cuponService;

    @Autowired
    private EmailService emailService;  // Servicio de correo inyectado

    public Pedido crearPedido(Usuario usuario, List<DetallePedido> detalles, Double total, String codigoCupon) throws Exception {
        // Verificar stock de cada producto
        for (DetallePedido detalle : detalles) {
            Producto producto = detalle.getProducto();
            if (producto.getStock() < detalle.getCantidad()) {
                throw new Exception("Stock insuficiente para el producto: " + producto.getNombre());
            }
        }

        // Aplicar el cupón si está disponible
        if (codigoCupon != null && !codigoCupon.isEmpty()) {
            Cupon cupon = cuponService.aplicarCupon(codigoCupon);
            total = total - (total * (cupon.getDescuento() / 100)); // Aplicar descuento en porcentaje
        }

        // Reducir el stock de los productos y guardar los cambios
        for (DetallePedido detalle : detalles) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
        }

        // Crear y guardar el pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDetalles(detalles);
        pedido.setFecha(new Date());
        pedido.setTotal(total);

        Pedido nuevoPedido = pedidoRepository.save(pedido);

        // Enviar confirmación por correo
        enviarConfirmacionPedido(usuario.getEmail());

        return nuevoPedido;
    }

    // Actualizar estado del pedido
    public void actualizarEstadoPedido(Long idPedido, String nuevoEstado) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido no encontrado"));

        pedido.setEstado(nuevoEstado); // Actualizar el estado del pedido
        pedidoRepository.save(pedido);
    }

    // Cancelar pedido y restaurar el stock
    public void cancelarPedido(Long idPedido) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new Exception("Pedido no encontrado"));

        // Verificar si el pedido no ha sido enviado aún
        if (!pedido.getEstado().equals("Enviado")) {
            // Devolver el stock de los productos
            for (DetallePedido detalle : pedido.getDetalles()) {
                Producto producto = detalle.getProducto();
                producto.setStock(producto.getStock() + detalle.getCantidad());
                productoRepository.save(producto);
            }

            // Actualizar el estado del pedido cancelado
            pedido.setEstado("Cancelado");
            pedidoRepository.save(pedido);
        } else {
            throw new Exception("No se puede cancelar un pedido que ya ha sido enviado");
        }
    }

    public List<Pedido> obtenerPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    // Método para enviar confirmación de pedido por correo
    public void enviarConfirmacionPedido(String destinatario) {
        emailService.enviarCorreo(destinatario, "Confirmación de Pedido", "Tu pedido ha sido recibido y está en proceso.");
    }
}



