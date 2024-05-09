package com.mercurio.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mercurio.game.Screen.MercurioMain;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1024, 720);
		config.setResizable(false);
		config.setTitle("PokemonMercurio");
		config.setWindowIcon("menuImage/logo.png");
		new Lwjgl3Application(new MercurioMain(), config);
	}
}
