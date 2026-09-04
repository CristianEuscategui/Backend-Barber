package com.barberpro.backend.service;

import com.barberpro.backend.entity.Cita;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Service
public class WhatsAppService {

    private static final String NUMERO_NEGOCIO = "51999888777";

    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    public String generarLinkWhatsApp(Cita cita) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' hh:mm a");
        String fechaTxt = cita.getFechaHora().format(fmt);

        String mensaje = String.format(
            "¡Hola! Deseo confirmar mi reserva:\n\n" +
            "👤 *Cliente:* %s\n" +
            "✂️ *Servicio:* %s (S/ %.2f)\n" +
            "💈 *Barbero:* %s\n" +
            "📅 *Fecha:* %s\n\n" +
            "Quedo atento a la confirmación.",
            cita.getClienteNombre(),
            cita.getServicio().getNombre(),
            cita.getServicio().getPrecio(),
            cita.getBarbero().getNombre(),
            fechaTxt
        );

        String encodedMsg = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        return "https://api.whatsapp.com/send?phone=" + NUMERO_NEGOCIO + "&text=" + encodedMsg;
    }

    // Nuevo: link para pedirle al CLIENTE su reseña, una vez su cita está TERMINADA
    public String generarLinkResena(Cita cita) {
        String linkResena = frontendUrl + "/resena/" + cita.getId();

        String mensaje = String.format(
            "¡Hola %s! Gracias por confiar en nosotros. 💈\n\n" +
            "¿Cómo te fue con %s? Nos encantaría conocer tu opinión, toma menos de un minuto:\n\n" +
            "%s",
            cita.getClienteNombre(),
            cita.getBarbero().getNombre(),
            linkResena
        );

        String telefonoLimpio = cita.getClienteTelefono().replaceAll("[^0-9]", "");
        String encodedMsg = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        return "https://api.whatsapp.com/send?phone=" + telefonoLimpio + "&text=" + encodedMsg;
    }

    public String generarLinkNotificacionBarbero(Cita cita) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' hh:mm a");
    String fechaTxt = cita.getFechaHora().format(fmt);

    String mensaje = String.format(
        "📅 *Nueva cita agendada*\n\n" +
        "👤 *Cliente:* %s\n" +
        "📞 *Teléfono:* %s\n" +
        "✂️ *Servicio:* %s\n" +
        "🕐 *Fecha:* %s\n\n" +
        "Si no puedes atenderla, avisa al negocio lo antes posible.",
        cita.getClienteNombre(),
        cita.getClienteTelefono(),
        cita.getServicio() != null ? cita.getServicio().getNombre() : "Sin especificar",
        fechaTxt
    );

    String telefonoBarbero = cita.getBarbero().getTelefono().replaceAll("[^0-9]", "");
    String encodedMsg = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
    return "https://api.whatsapp.com/send?phone=" + telefonoBarbero + "&text=" + encodedMsg;
}

public String generarLinkCancelacion(Cita cita) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' hh:mm a");
    String fechaTxt = cita.getFechaHora().format(fmt);

    String mensaje = String.format(
        "Hola %s, lamentamos informarte que tu cita del %s ha sido cancelada. " +
        "Por favor contáctanos para reprogramar en el horario que prefieras. Disculpa las molestias.",
        cita.getClienteNombre(),
        fechaTxt
    );

    String telefonoCliente = cita.getClienteTelefono().replaceAll("[^0-9]", "");
    String encodedMsg = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
    return "https://api.whatsapp.com/send?phone=" + telefonoCliente + "&text=" + encodedMsg;
}
}