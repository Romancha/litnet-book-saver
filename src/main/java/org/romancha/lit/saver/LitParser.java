package org.romancha.lit.saver;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        System.out.println("Open book: " + bookUrl);
        open(bookUrl);

        System.out.println("Wait before start: " + waitBeforeStart + " seconds");
        TimeUnit.SECONDS.sleep(waitBeforeStart);

        int pageNumber = 1;
        int pageEqualsCount = 0;
        int pageEqualsMax = 15;

        String previousPart = "";

        while (true) {
            try {
                System.out.println("Parse page: " + pageNumber);
                TimeUnit.MILLISECONDS.sleep(1300);

                String cleanedPart = LitBook.getCleanedDocumentContent();
                if (previousPart.equals(cleanedPart)) {
                    pageEqualsCount++;

                    System.out.println("Page is equal: " + pageEqualsCount);
                    System.out.println("Sleep 1 sec");
                    TimeUnit.SECONDS.sleep(1);

                    if (pageEqualsCount > pageEqualsMax) {
                        System.out.println("Page is equal more than " + pageEqualsMax + " times. Go to text page.");
                        pageEqualsCount = 0;

                        LitBook.goToNextPage();
                    }
                    continue;
                }

                pageEqualsCount = 0;

                previousPart = cleanedPart;

                result.append(getPageNumberHtml(pageNumber));
                result.append(cleanedPart);

                pageNumber++;

                int randomDelay = getRandomDelay();
                System.out.println("wait: " + randomDelay + " seconds");
                TimeUnit.SECONDS.sleep(randomDelay);

                atBottom();
                System.out.println("to bottom");

                LitBook.closeModals();
                System.out.println("close modals");

                if (LitBook.nextButtonIsClickable()) {
                    LitBook.goToNextPage();
                    System.out.println("next page");
                } else {
                    System.out.println("break");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error while trying to parse: " + e);
            }
        }

        System.out.println("Write file to: " + resultFileName);

        try {
            FileUtils.writeStringToFile(new File(resultFileName), result.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error while try to save result file: " + e);
        }
    }

    private String getPageNumberHtml(int page) {
        return "<h3>Страница: " + page + "</h3>";
    }

    private int getRandomDelay() {
        return (int) ((Math.random() * (partDelayMax - partDelayMin)) + partDelayMin);
    }

}
