package org.romancha.lit.saver;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.atBottom;
import static com.codeborne.selenide.Selenide.open;

public class LitParser {

    private final String bookUrl;
    private final String resultFileName;
    private final int waitBeforeStart;
    private final int partDelayMin;
    private final int partDelayMax;

    public LitParser(String bookUrl, String resultFileName, int waitBeforeStart, int partDelayMin, int partDelayMax) {
        this.bookUrl = bookUrl;
        this.resultFileName = resultFileName;
        this.waitBeforeStart = waitBeforeStart;
        this.partDelayMin = partDelayMin;
        this.partDelayMax = partDelayMax;
    }

    public void generateHtml() throws InterruptedException {
        StringBuilder result = new StringBuilder();

        open(bookUrl);

        TimeUnit.SECONDS.sleep(waitBeforeStart);

        while (true) {
            try {
                String cleanedPart = LitBook.getCleanedDocumentContent();

                result.append(cleanedPart);

                TimeUnit.SECONDS.sleep(getRandomDelay());

                atBottom();

                LitBook.closeModals();

                if (LitBook.nextButtonIsClickable()) {
                    LitBook.goToNextPage();
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error while trying to parse: " + e);
            }
        }

        try {
            FileUtils.writeStringToFile(new File(resultFileName), result.toString(), "UTF-8");
        } catch (IOException e) {
            System.out.println("Error while try to save result file: " + e);
        }
    }

    private int getRandomDelay() {
        return (int) ((Math.random() * (partDelayMax - partDelayMin)) + partDelayMin);
    }

}
