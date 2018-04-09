package paint.model;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PluginManager {
	public ArrayList<CommandPane> loadPlugins() {

		ArrayList<CommandPane> commands = new ArrayList<>();
		String Path = System.getProperty("user.dir") + "/";
		URL dirUrl = null;
		try {
			dirUrl = new URL("file:///" + Path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("\nOpening " + Path + "src" + File.separator + "plugin" + File.separator + " for plugins\n");

		try {
			File file = new File(dirUrl.getPath() + "src" + File.separator + "plugin");
			String[] shapesNames = file.list();
			for (String name : shapesNames) {
				if (new File(dirUrl.getPath()).isDirectory() && name.charAt(0) != '.') {
					CommandPane newTool = loadTool(name);
					if (newTool != null) {

						commands.add(newTool);
					}
				}
			}
		} catch (NullPointerException e) {
			System.out.print("Plugins directory not found!\nCannot initialize plugins");
		}

		return commands;
	}

	private PluginManifest getManifest(String tool) {

		URL jsonUrl = null;
		try {
			jsonUrl = new URL("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ "plugin" + File.separator + tool + File.separator + "manifest.json");
		} catch (MalformedURLException e) {
			System.out.print("Cannot load " + tool + "'s manifest.json!");
		}
		try (Reader reader = new InputStreamReader(jsonUrl.openStream(), "UTF-8")) {
			Gson gson = new GsonBuilder().create();
			PluginManifest manifest = gson.fromJson(reader, PluginManifest.class);
			return manifest;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	private CommandPane loadTool(String tool) {

		CommandPane newCommand = null;
		PluginManifest manifest = getManifest(tool);
		if (manifest == null) {
			System.out.print("Unable to load " + tool);
			return null;
		}
		String className = null;
		String packageName = null;
		String toolName = null;
		URL classUrl = null;

		className = manifest.getClassName();
		packageName = manifest.getPackageName();
		toolName = manifest.getName();

		if (className != null && packageName != null) {
			try {
				classUrl = new URL("file:///" + System.getProperty("user.dir") + File.separator + "plugin"
						+ File.separator + tool + File.separator + className + ".class");
				System.out.print(classUrl);

			} catch (MalformedURLException e) {
				System.out.print("Unable to load " + tool);
			}
			URL[] urls = new URL[] { classUrl };
			@SuppressWarnings("resource")
			ClassLoader classLoader = new URLClassLoader(urls);

			try {
				Class<?> cls = classLoader.loadClass(packageName + "." + className);
				newCommand = (CommandPane) cls.newInstance();

				System.out.print("Initialized " + toolName + "!\n");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return newCommand;
	}
}
