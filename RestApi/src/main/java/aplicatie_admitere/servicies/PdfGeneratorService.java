package aplicatie_admitere.servicies;

import com.itextpdf.text.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.itextpdf.text.pdf.PdfWriter;

import javax.naming.MalformedLinkException;

@Service

public class PdfGeneratorService {

    @SneakyThrows
    public byte[] genereazaLegitimatie(String name, String cnp, String phone,Long id) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        try{
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.setPageSize(new Rectangle(600, 320));
            document.open();
            URL url=getClass().getResource("/stema.png");
            Image image = Image.getInstance(url);
            image.scaleToFit(100, 100);
            image.setAbsolutePosition(10, 10);
            document.add(image);

            Paragraph title = new Paragraph("ADMITERE ACADEMIA TEHNICA MILITARA");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(""));
            Font font = new Font(Font.FontFamily.HELVETICA, 12);
            Paragraph subtitle = new Paragraph("LEGITIMATIE DE CANDIDAT", font);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            Paragraph p = new Paragraph("NR. Candidat: ");
            p.add(new Chunk("" + id, boldFont));
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Paragraph p1 = new Paragraph("Nume: ");
            p1.add(new Chunk("" + name, boldFont));
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);

            Paragraph p2 = new Paragraph("CNP: ");
            p2.add(new Chunk("" + cnp, boldFont));
            p2.setAlignment(Element.ALIGN_CENTER);
            document.add(p2);

            Paragraph p3 = new Paragraph("Telefon: ");
            p3.add(new Chunk("" + phone, boldFont));
            p3.setAlignment(Element.ALIGN_CENTER);
            document.add(p3);
            document.close();
        }
        catch(DocumentException e)
        {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


}
