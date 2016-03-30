package highlighter;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pdfclown.documents.Page;
import org.pdfclown.documents.contents.ITextString;
import org.pdfclown.documents.contents.TextChar;
import org.pdfclown.documents.interaction.annotations.TextMarkup;
import org.pdfclown.documents.interaction.annotations.TextMarkup.MarkupTypeEnum;
import org.pdfclown.files.File;
import org.pdfclown.files.SerializationModeEnum;
import org.pdfclown.tools.TextExtractor;
import org.pdfclown.util.math.Interval;
import org.pdfclown.util.math.geom.Quad;

public class PDFHighlighter {

    public void textHighlight() {

        try {

            File inputFile = new File("D:\\opu.pdf");

            String textRegEx = "account";
            Pattern pattern = Pattern.compile(textRegEx, Pattern.CASE_INSENSITIVE);

            TextExtractor textExtractor = new TextExtractor(true, true);

            for (final Page page : inputFile.getDocument().getPages()) {

                System.out.println("\nScanning page " + (page.getIndex() + 1) + "...\n");
                // Extract the page text!
                Map<Rectangle2D, List<ITextString>> textStrings = textExtractor.extract(page);
                System.out.println(page);
                // Find the text pattern matches!
                final Matcher matcher = pattern.matcher(TextExtractor.toString(textStrings));

                // Highlight the text pattern matches!
                textExtractor.filter(textStrings, new TextExtractor.IIntervalFilter() {
                    @Override public boolean hasNext() {
                        return matcher.find();
                    }

                    @Override public Interval next() {
                        return new Interval(matcher.start(), matcher.end());
                    }

                    @Override public void process(Interval interval, ITextString match) {
                        // Defining the highlight box of the text pattern match...
                        List highlightQuads = new ArrayList();

                        Rectangle2D textBox = null;
                        for (TextChar textChar : match.getTextChars()) {
                            Rectangle2D textCharBox = textChar.getBox();
                            if (textBox == null) {
                                textBox = (Rectangle2D) textCharBox.clone();
                            } else {
                                if (textCharBox.getY() > textBox.getMaxY()) {
                                    highlightQuads.add(Quad.get(textBox));
                                    textBox = (Rectangle2D) textCharBox.clone();
                                } else {
                                    textBox.add(textCharBox);
                                }
                            }
                        }
                        highlightQuads.add(Quad.get(textBox));
                        new TextMarkup(page, null, MarkupTypeEnum.Highlight, highlightQuads);
                    }

                    @Override public void remove() {
                        throw new UnsupportedOperationException();
                    }
                });
            }

            inputFile.save(SerializationModeEnum.Incremental);
            System.out.println("Process Done");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
