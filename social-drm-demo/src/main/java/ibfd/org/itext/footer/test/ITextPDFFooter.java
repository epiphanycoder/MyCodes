package ibfd.org.itext.footer.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;


public class ITextPDFFooter {

    public void process() {

        Rectangle pagesize = new Rectangle(216f, 720f);
        Document document = new Document(pagesize, 36f, 72f, 108f, 180f);

    }

    public static void main(String[] args) {
        ITextPDFFooter iTextPDFFooter = new ITextPDFFooter();
        iTextPDFFooter.process();
    }
}
