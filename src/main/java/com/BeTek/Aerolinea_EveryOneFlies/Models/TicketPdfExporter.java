package com.BeTek.Aerolinea_EveryOneFlies.Models;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

import java.awt.*;

@Component
public class TicketPdfExporter {

    @Autowired
    private final TemplateEngine templateEngine;

    public TicketPdfExporter(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] createPdf(String templateName, ModelMap model) {
        // Create a Thymeleaf context and set the variables for the template
        Context context = new Context();
        context.setVariables(model);

        // Process the template and model using Thymeleaf, which generates the HTML
        String html = templateEngine.process(templateName, context);

        // Convert the generated HTML to PDF using Flying Saucer and return as byte[]
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(out);
        } catch (DocumentException e) {
            throw new RuntimeException("Error while creating PDF", e);
        }
        return out.toByteArray();
    }
}
