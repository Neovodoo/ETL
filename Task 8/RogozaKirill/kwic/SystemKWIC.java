package kwic;

import java.io.*;
import kwic.filters.*;

public class SystemKWIC {

    public static void main(String[] args) throws IOException {

        // Создаем пайпы для связи между фильтрами
        PipedOutputStream inputToShifter = new PipedOutputStream();
        PipedInputStream shifterInput = new PipedInputStream(inputToShifter);

        PipedOutputStream shifterToAlphabetizer = new PipedOutputStream();
        PipedInputStream alphabetizerInput = new PipedInputStream(shifterToAlphabetizer);

        PipedOutputStream alphabetizerToOutput = new PipedOutputStream();
        PipedInputStream outputInput = new PipedInputStream(alphabetizerToOutput);

        // Создаем фильтры (Input - захардкоден внутри run)
        Thread inputFilter = new Thread(new InputFilter(inputToShifter));
        Thread shifterFilter = new Thread(new CircularShifterFilter(shifterInput, shifterToAlphabetizer));
        Thread alphabetizerFilter = new Thread(new AlphabetizerFilter(alphabetizerInput, alphabetizerToOutput));
        Thread outputFilter = new Thread(new OutputFilter(outputInput));

        // Запускаем фильтры
        outputFilter.start();
        alphabetizerFilter.start();
        shifterFilter.start();
        inputFilter.start();
    }

}
