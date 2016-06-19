package ibfd.org.social.drm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.ibfd.common.util.FooterUtil;
import org.ibfd.common.util.SocialDrmPdfException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class SocialDRMDemo {
    private final String drmText;


    public SocialDRMDemo(String drmText) {
        this.drmText = drmText;
    }

    //    public static void main(String[] args) {
    //        //        String[] files = { "aptb_2015_06_hk_1", "aptb011108", "cta_nl", "dfi051207", "gtha_nl", "oecd_beps_action_8_10_final_report_2015",
    //        //                "oecd_beps_developments", "oecd_tt_report_tx_sparing_1997", "porlan", "tp_nl" };
    //        String[] files = { "aptb011108" };
    //        FileNamesFactory fileNamesFactory = new FileNamesFactory("_drm_itext", ".pdf");
    //        String socialDRMText = FooterUtil.exportedBy("some.user@ibfd.org");
    //        SocialDRMDemo socialDRMDemo = new SocialDRMDemo(socialDRMText);
    //        for (String file : files) {
    //            FileNames fileNames = fileNamesFactory.create(file);
    //            try {
    //                socialDRMDemo.addSocialDRMToPDF(fileNames);
    //            } catch (Exception e) {
    //                System.err.println("failed to process " + fileNames.input + ": " + e.getMessage());
    //            }
    //        }
    //    }

    public static void main(String[] args) {
        String[] files = { "aptb011108" };
        FileNamesFactory fileNamesFactory = new FileNamesFactory("_drm_itext", ".pdf");
        for (String file : files) {
            FileNames fileNames = fileNamesFactory.create(file);
            try {
                PdfReader reader = new PdfReader(SocialDRMDemo.class.getResourceAsStream("/aptb011108.pdf"));
                addSocialDRMtoPDF(reader, "mshahriar@gmail.com", new FileOutputStream(new File("out", "/aptb011108.pdf")));
                System.out.println("processing done for " + file);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public void addSocialDRMToPDF(FileNames fileNames) throws IOException, DocumentException {
        PerformanceTimer timer = new PerformanceTimer();
        PdfReader reader = new PdfReader(getClass().getResourceAsStream(fileNames.input));
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(fileNames.output));
        Phrase phrase = new Phrase(drmText, new Font(FontFamily.HELVETICA, 8));
        float y = 5;
        int pageCount = reader.getNumberOfPages();
        for (int page = 1; page <= pageCount; page++) {
            Rectangle pagesize = reader.getPageSizeWithRotation(page);
            float x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            PdfContentByte over = stamper.getOverContent(page);
            over.saveState();
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, phrase, x, pagesize.getBottom() + y, 0);
            over.restoreState();
        }
        stamper.close();
        reader.close();
        System.out.println(timer.report("processing " + pageCount + " pages of " + fileNames.input));
    }

    /**
     * Add social DRM statement to a PDF.
     *
     * @param reader the PDF reader
     * @param user the user name
     * @aram outputStream the stream to write the new PDF to.
     * @throws SocialDrmPdfException if the social DRM text could not be added to the PDF.
     */
    private static void addSocialDRMtoPDF(PdfReader reader, String user, OutputStream outputStream) throws SocialDrmPdfException {
        try {
            PdfStamper stamper = new PdfStamper(reader, outputStream);
            Phrase phrase = new Phrase(FooterUtil.exportedBy(user), new Font(FontFamily.HELVETICA, 8));
            float y = 5;
            int pageCount = reader.getNumberOfPages();
            for (int page = 1; page <= pageCount; page++) {
                Rectangle pagesize = reader.getPageSizeWithRotation(page);
                float x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                PdfContentByte over = stamper.getOverContent(page);
                over.saveState();
                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, phrase, x, y, 0);
                over.restoreState();
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            String msg = "failed to add social DRM to PDF" + (StringUtils.isEmpty(user) ? "" : " for user " + user) + ": " + e.getMessage();
            throw new SocialDrmPdfException(msg, e);
        }
    }

    private static class FileNames {
        private final String input, output;

        public FileNames(String baseName, String suffix, String extension) {
            input = "/" + baseName + extension;
            output = baseName + suffix + extension;
        }
    }

    private static class FileNamesFactory {
        private final String extension, suffix;

        public FileNamesFactory(String extension, String suffix) {
            this.extension = extension;
            this.suffix = suffix;
        }

        public FileNames create(String baseName) {
            return new FileNames(baseName, extension, suffix);
        }
    }
}
