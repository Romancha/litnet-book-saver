package org.romancha.lit.saver;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

public class App {

    public static void main(String[] args) throws InterruptedException, ParseException {
        Options options = createOptions();

        CommandLineParser cmdParser = new DefaultParser();
        CommandLine cmd = cmdParser.parse(options, args);

        if (cmd.hasOption(LitOptions.HELP)) {
            HelpFormatter fmt = new HelpFormatter();
            fmt.printHelp("Help", options);
            return;
        }

        String url = cmd.getOptionValue(LitOptions.URL);
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("Url book is mandatory parameter");
        }

        String file = cmd.getOptionValue(LitOptions.FILE, "./resultBook.html");
        String wait = cmd.getOptionValue(LitOptions.WAIT, "10");
        String delayMin = cmd.getOptionValue(LitOptions.DELAY_MIN, "2");
        String delayMax = cmd.getOptionValue(LitOptions.DELAY_MAX, "10");

        LitParser parser = new LitParser(url, file,
                Integer.parseInt(wait), Integer.parseInt(delayMin), Integer.parseInt(delayMax)
        );

        parser.generateHtml();
    }

    private static Options createOptions() {
        Options options = new Options();

        options.addOption(new Option(LitOptions.HELP, "help", false, "Print help"));
        options.addOption(new Option(LitOptions.URL, "url", true,
                "Litnet book url. First page."));
        options.addOption(new Option(LitOptions.FILE, "file", true, "" +
                "File name to save. Should be in html format."));
        options.addOption(new Option(LitOptions.WAIT, "wait", true,
                "Wait seconds before start parse. On this delay you can login on you Litnet account, " +
                        "for private books."));
        options.addOption(new Option(LitOptions.DELAY_MIN, "delayMin", true,
                "Min delay (seconds) before parse next page."));
        options.addOption(new Option(LitOptions.DELAY_MAX, "delayMax", true,
                "Max delay (seconds) before parse next page."));

        return options;
    }

}
