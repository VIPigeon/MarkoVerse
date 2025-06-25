
import java.util.*;
import java.io.IOException;
import java.nio.file.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BuiltinServer {

    public static void main(String[] args) {
        Path filePath = Paths.get("..", "demo", "data", "input.json").toAbsolutePath().normalize();
        Path dir = filePath.getParent();
        System.out.println("Searching file: " + filePath);        

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            dir.register(watchService,
                         StandardWatchEventKinds.ENTRY_MODIFY,
                         StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println("Watching file: " + filePath);

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted");
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    Path changed = (Path) event.context();
                    if (changed.endsWith("input.json")) {
                        System.out.println("File input.json was modified or created.");
                        readJsonFile(filePath);
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    System.out.println("WatchKey is invalid");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    private static void readJsonFile(Path filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputData data = mapper.readValue(filePath.toFile(), InputData.class);

            // System.out.println("name: " + data.name);

            char[][] wordChars = data.getWordAsCharArray();
            // System.out.println("word as chars:");
            // for (char[] row : wordChars) {
            //     for (char c : row) {
            //         System.out.print((int) c + " ");  // печатаем числовые коды символов
            //     }
            //     System.out.println();
            // }

            // System.out.println("rules:");
            List<Pair<String, String>> rules = new ArrayList<>();
            for (Rule rule : data.rules) {
                // System.out.println((int)rule.pattern.charAt(0));
                // System.out.println((int)rule.replacement_string.charAt(0));
                rules.add(new Pair<String, String>(rule.pattern, rule.replacement_string));
            }
            MarkoVerse2D markoverse2D = new MarkoVerse2D();
            markoverse2D.build(wordChars, rules);

            // todo (если в GUI реализовано больше одного метода): сделать зависимость работы markoverse от name
            // if (name == "")
            writeJsonFile(markoverse2D.run());


        } catch (IOException e) {
            System.out.println("Failed to read JSON: " + e.getMessage());
        }
    }

    private static void writeJsonFile(char[][] result) {
        Path filePath = Paths.get("..", "demo", "data", "output.json").toAbsolutePath().normalize();
        // for (char[] row : result) {
        //     for (char c : row) {
        //         System.out.print((int) c + " ");  // печатаем числовые коды символов
        //     }
        //     System.out.println();
        // }

        // Преобразуем char[][] в List<List<Integer>>
        List<List<Integer>> finalResult = new ArrayList<>();
        for (char[] row : result) {
            List<Integer> intRow = new ArrayList<>();
            for (char c : row) {
                intRow.add((int) c);
            }
            finalResult.add(intRow);
        }

        // Оборачиваем в карту с ключом "final"
        Map<String, Object> output = new HashMap<>();
        output.put("final", finalResult);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), output);
            System.out.println("Output written to: " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to write output JSON: " + e.getMessage());
        }
    }
}
