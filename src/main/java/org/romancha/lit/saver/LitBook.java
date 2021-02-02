package org.romancha.lit.saver;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LitBook {

    private static final List<String> CLASSES_TO_REMOVE = Arrays.asList("reader-pagination", "clearfix", "popup-modal",
            "block b-recommended widget-block");

    public static SelenideElement getReaderText() {
        return $(By.xpath("//div[contains(@class, 'reader-text font-size-medium')]")).should(Condition.exist);
    }

    public static void goToNextPage() {
        $(By.id("link-right")).shouldBe(Condition.exist).shouldBe(Condition.enabled).click();
    }

    public static boolean nextButtonIsClickable() {
        SelenideElement button = $(By.id("link-right")).shouldBe(Condition.exist);

        String onclick = button.getAttribute("onclick");
        String href = button.getAttribute("href");

        return button.isDisplayed() && button.isEnabled()
                && (StringUtils.isNotBlank(onclick) || StringUtils.isNotBlank(href));
    }

    public static String getCleanedDocumentContent() {
        Document doc = Jsoup.parse(LitBook.getReaderText().innerHtml());

        CLASSES_TO_REMOVE.forEach(doc::getElementsByClass);
        doc.getElementsByTag("script").remove();
        doc.getElementsByTag("div").remove();
        doc.getElementsByAttributeValueContaining("style", "-14px").remove();

        return doc.toString();
    }

    public static void closeModals() {
        ElementsCollection modalElementsToClose = $$(By.xpath("//div[contains(@class, " +
                "'modal-content')]//button[contains(@class, 'close')]"));
        for (SelenideElement element : modalElementsToClose) {
            if (element.isDisplayed() && element.isEnabled()) {
                element.click();
            }
        }
    }
}
