package example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class MarkovApplication extends Application {

    // Определение палитры PICO-8
    private static final Color[] PICO8_PALETTE = {
            Color.rgb(0, 0, 0),   // 0 - черный
            Color.rgb(29, 43, 83), // 1 - темно-синий
            Color.rgb(126, 37, 83), // 2 - фиолетовый
            Color.rgb(0, 135, 81), // 3 - зеленый
            Color.rgb(171, 82, 54), // 4 - коричневый
            Color.rgb(95, 87, 79), // 5 - серый
            Color.rgb(194, 195, 199), // 6 - светло-серый
            Color.rgb(255, 241, 232), // 7 - белый
            Color.rgb(255, 0, 77), // 8 - розовый
            Color.rgb(255, 163, 0), // 9 - оранжевый
            Color.rgb(255, 255, 0), // 10 - желтый
            Color.rgb(0, 255, 0), // 11 - ярко-зеленый
            Color.rgb(0, 255, 255), // 12 - ярко-голубой
            Color.rgb(0, 0, 255), // 13 - синий
            Color.rgb(255, 0, 255), // 14 - фиолетовый
            Color.rgb(255, 0, 0)  // 15 - красный
    };
    private static final int n = 5;
    private static final int m = 5;
    private static final String INPUT_FILE = "data\\input.json";
    private static final String OUTPUT_FILE = "data\\output.json";

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(10);

        Canvas canvas = new Canvas(n * 20, m * 20);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawImageFromFile(gc);

        vbox.getChildren().add(canvas);

        Button button1 = new Button("final");
        button1.setOnAction(event -> saveMatrixToFile("final"));
        vbox.getChildren().add(button1);

        Button button2 = new Button("process");
        button2.setOnAction(event -> saveMatrixToFile("process"));
        vbox.getChildren().add(button2);

        Scene scene = new Scene(vbox, 150, 300);
        primaryStage.setTitle("PICO-8 Image");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveMatrixToFile(String prompt) {
        try {
            // Чтение содержимого исходного файла
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_FILE)));
            JSONObject jsonObject = new JSONObject(content);

            // Изменение значения поля name
            jsonObject.put("name", prompt);

            // Запись измененного JSON в выходной файл
            Files.write(Paths.get(INPUT_FILE), jsonObject.toString(4).getBytes());

            System.out.println("Копирование завершено успешно.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void drawImageFromFile(GraphicsContext gc) {
        // Чтение матрицы из файла и рисование на Canvas
        try (BufferedReader br = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    char symbol = line.charAt(x);
                    int colorIndex = Character.getNumericValue(symbol);
                    if (colorIndex >= 0 && colorIndex < PICO8_PALETTE.length) {
                        gc.setFill(PICO8_PALETTE[colorIndex]);
                        gc.fillRect(x * 20, y * 20, 20, 20);
                    }
                }
                y++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

