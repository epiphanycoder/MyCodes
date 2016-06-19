package ibfd.org.social.drm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.ibfd.common.lang.DateUtil;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;

public class DRMDemo {

    public static void main(String[] args) {
        String[] files = { "aptb_2015_06_hk_1", "aptb011108", "cta_nl", "dfi051207", "gtha_nl", "oecd_beps_action_8_10_final_report_2015",
                "oecd_beps_developments", "oecd_tt_report_tx_sparing_1997", "porlan", "tp_nl" };
        for (String file : files) {
            try {
                InputStream fileInputStream = DRMDemo.class.getResourceAsStream("/" + file + ".pdf");
                FileOutputStream fileOutputStream = new FileOutputStream(new File("out", "/" + file + ".pdf"));

                byte[] input = IOUtils.toByteArray(fileInputStream);
                byte[] output = doDRMToPDF(input, "mshahriar@gmail.com");

                fileInputStream.close();
                fileOutputStream.write(output);
                fileOutputStream.flush();
            } catch (Exception e) {
                System.err.println(e);
            }
            System.out.println("Processing  done for file " + file + ".pdf");
        }
    }

    private static byte[] doDRMToPDF(byte[] input, String user) {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
            PdfReader reader = new PdfReader(input);

            PdfStamper stamper = new PdfStamper(reader, outputStream);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            TextMarginFinder finder;

            Phrase phrase = new Phrase(exportedBy(user), new Font(FontFamily.HELVETICA, 8));
            float y = 5;
            int pageCount = reader.getNumberOfPages();

            for (int page = 1; page <= pageCount; page++) {

                finder = parser.processContent(page, new TextMarginFinder());

                try {

                    y = Math.abs(finder.getUry() - (finder.getHeight() + 15));

                    Rectangle pagesize = reader.getPageSizeWithRotation(page);
                    float x = (pagesize.getLeft() + pagesize.getRight()) / 2;

                    PdfContentByte over = stamper.getOverContent(page);
                    over.saveState();
                    ColumnText.showTextAligned(over, Element.ALIGN_CENTER, phrase, x, y, 0);
                    over.restoreState();

                } catch (Exception e) {
                    continue;
                }
            }
            stamper.close();
            reader.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
        }
        return null;
    }


    private static String exportedBy(String userName) {
        String today = DateUtil.getTodayWithAbbriviatedMothFormat();
        String exportedBy = "Exported / Printed on " + today;
        if (userName != null && !userName.trim().isEmpty()) {
            exportedBy += " by " + userName + ".";
        } else {
            exportedBy += ".";
        }
        return exportedBy;
    }
}
