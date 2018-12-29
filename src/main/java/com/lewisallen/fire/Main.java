package com.lewisallen.fire;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application
{

    private Color[] colours = new Color[37];
    private int[][] screen = new int[512][256];

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Pixel Fire");
        Group root = new Group();
        Scene scene = new Scene(root, Color.web("#000000"));
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(screen.length, screen[0].length);
        root.getChildren().add(canvas);

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final PixelWriter pc = gc.getPixelWriter();

        initColours();

        // Run at 60 fps
        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime){
                updateColours();
                drawScreen(gc, pc);
            }
        }.start();

        primaryStage.show();
    }

    private void initColours()
    {
        // Initialise array of colours.
        colours[0] = Color.web("#000000");
        colours[1] = Color.web("#070707");
        colours[2] = Color.web("#1f0707");
        colours[3] = Color.web("#2f0f07");
        colours[4] = Color.web("#470f07");
        colours[5] = Color.web("#571707");
        colours[6] = Color.web("#671f07");
        colours[7] = Color.web("#771f07");
        colours[8] = Color.web("#8f2707");
        colours[9] = Color.web("#9f2f07");
        colours[10] = Color.web("#af3f07");
        colours[11] = Color.web("#bf4707");
        colours[12] = Color.web("#c74707");
        colours[13] = Color.web("#DF4F07");
        colours[14] = Color.web("#DF5707");
        colours[15] = Color.web("#DF5707");
        colours[16] = Color.web("#D75F07");
        colours[17] = Color.web("#D7670F");
        colours[18] = Color.web("#cf6f0f");
        colours[19] = Color.web("#cf770f");
        colours[20] = Color.web("#cf7f0f");
        colours[21] = Color.web("#CF8717");
        colours[22] = Color.web("#C78717");
        colours[23] = Color.web("#C78F17");
        colours[24] = Color.web("#C7971F");
        colours[25] = Color.web("#BF9F1F");
        colours[26] = Color.web("#BF9F1F");
        colours[27] = Color.web("#BFA727");
        colours[28] = Color.web("#BFA727");
        colours[29] = Color.web("#BFAF2F");
        colours[30] = Color.web("#B7AF2F");
        colours[31] = Color.web("#B7B72F");
        colours[32] = Color.web("#B7B737");
        colours[33] = Color.web("#CFCF6F");
        colours[34] = Color.web("#DFDF9F");
        colours[35] = Color.web("#EFEFC7");
        colours[36] = Color.web("#FFFFFF");

        // Initially fill screen with black pixels.
        for(int x = 0; x < screen.length; x++)
        {
            for(int y = 0; y < screen[x].length; y++)
            {
                screen[x][y] = 0;
            }
        }


        // Populate the bottom line of pixels on the canvas.
        // Rest of pixel will propagate from the bottom line.
        for(int x = 0; x < screen.length; x++)
        {
            screen[x][screen[x].length-1] = colours.length-1;
        }
    }

    private void updateColours()
    {
        // Propagate pixels throughout screen.
        for(int x = 0; x < screen.length; x++)
        {
            for(int y = screen[x].length - 2; y >= 0; y--)
            {
                // Add randomness in the propagation
                double rand = Math.random();
                if(rand > 0.66)
                {
                    if(screen[x][y+1] != 0)
                        screen[x][y] = screen[x][y + 1] - ((int) Math.round(Math.random() * 100.0) & 1);
                }
                else if (rand > 0.33)
                {
                    if(x < screen.length - 1 && screen[x+1][y+1] != 0)
                        screen[x][y] = screen[x + 1][y + 1] - ((int) Math.round(Math.random() * 100.0) & 1);
                }
                else
                {
                    if(x > 1 && screen[x-1][y+1] != 0)
                        screen[x][y] = screen[x - 1][y + 1] - ((int) Math.round(Math.random() * 100.0) & 1);
                }
            }
        }
    }

    private void drawScreen(GraphicsContext gc, PixelWriter pc)
    {
        // Clear Screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Draw Fire Pixels
        for(int x = 0; x < screen.length; x++)
        {
            for(int y = 0; y < screen[x].length; y++)
            {
                pc.setColor(x, y, colours[screen[x][y]]);
            }
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
